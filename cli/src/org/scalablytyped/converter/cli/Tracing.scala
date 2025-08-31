package org.scalablytyped.converter.cli

import org.scalablytyped.converter.{Flavour, Selection}
import org.scalablytyped.converter.internal.importer.{
  Bootstrap,
  ConversionOptions,
  EnabledTypeMappingExpansion,
  LibScalaJs,
  LibTsSource,
  PersistingParser,
  Phase1ReadTypescript,
  Phase2ToScalaJs,
  PhaseFlavour
}
import org.scalablytyped.converter.internal.scalajs.{
  Minimization,
  Name,
  PackageTree,
  ParentsResolver,
  Printer,
  QualifiedName,
  TreeScope,
  Versions
}
import org.scalablytyped.converter.internal.ts.{PackageJson, TsIdentLibrary}
import org.scalablytyped.converter.internal.{InFolder, Json, constants, files}
import org.scalablytyped.converter.internal.logging._
import org.scalablytyped.converter.internal.phases.{PhaseListener, PhaseRes, PhaseRunner, RecPhase}
import org.scalablytyped.converter.internal.ts.CalculateLibraryVersion.PackageJsonOnly
import org.scalablytyped.converter.internal.maps._

import scala.collection.immutable.SortedSet
import org.scalablytyped.converter.internal.IArray

object Tracing {
  private val inDirectory = os.pwd
  val sourceOutputDir = os.pwd / "generated-sources"
  lazy val paths = new Paths(inDirectory)
  val parseCachePath = Some(files.existing(constants.defaultCacheFolder / "parse").toNIO)

  val logger: Logger[(Array[Logger.Stored], Unit)] =
    storing().zipWith(stdout.filter(LogLevel.warn))

  private val DefaultOptions = ConversionOptions(
    useScalaJsDomTypes = true,
    outputPackage = Name.typings,
    flavour = Flavour.Normal,
    enableScalaJsDefined = Selection.All,
    ignored = SortedSet("typescript"),
    stdLibs = SortedSet("es6"),
    versions = Versions(Versions.Scala3, Versions.ScalaJs1),
    expandTypeMappings = EnabledTypeMappingExpansion.DefaultSelection,
    enableLongApplyMethod = false,
    privateWithin = None,
    useDeprecatedModuleNames = false
  )

  def main(args: Array[String]): Unit = System.exit(mainNoExit(args))

  def mainNoExit(args: Array[String]): Int = {
    val packageJsonPath = paths.packageJson.getOrElse(sys.error(s"${inDirectory} does not contain package.json"))
    val nodeModulesPath = paths.node_modules.getOrElse(sys.error(s"${inDirectory} does not contain node_modules"))
    val packageJson: PackageJson = Json.force[PackageJson](packageJsonPath)

    val wantedLibs: SortedSet[TsIdentLibrary] = {
      val fromPackageJson = packageJson.allLibs(false, peer = true).keySet
      println(s"DEBUG: Libraries found in package.json: ${fromPackageJson.map(_.value).mkString(", ")}")
      require(fromPackageJson.nonEmpty, "No libraries found in package.json")
      val ret = fromPackageJson -- DefaultOptions.ignoredLibs
      println(s"DEBUG: Libraries after filtering ignored: ${ret.map(_.value).mkString(", ")}")
      require(ret.nonEmpty, s"All libraries in package.json ignored")
      ret
    }

    println(s"DEBUG: Standard library configuration: ${DefaultOptions.stdLibs.mkString(", ")}")
    val bootstrapped: Bootstrap.Bootstrapped =
      Bootstrap.fromNodeModules(InFolder(nodeModulesPath), DefaultOptions, wantedLibs)
    println(s"DEBUG: Bootstrap completed, found ${bootstrapped.libraryResolver.stdLib.libName.value} as stdlib")

    val sources: Vector[LibTsSource] = {
      bootstrapped.initialLibs match {
        case Left(unresolved) => sys.error(unresolved.msg)
        case Right(initial)   => initial
      }
    }

    println(s"DEBUG: Initial sources from bootstrap: ${sources.map(_.libName.value).mkString(", ")}")
    println(s"Converting ${sources.map(_.libName.value).mkString(", ")} to scalajs...")

    val cachedParser = PersistingParser(parseCachePath, bootstrapped.inputFolders, logger.void)

    // Step 1: Parse TypeScript files
    println("Step 1: Parsing TypeScript files...")
    val phase1 = new Phase1ReadTypescript(
      resolve = bootstrapped.libraryResolver,
      calculateLibraryVersion = PackageJsonOnly,
      ignored = DefaultOptions.ignoredLibs,
      ignoredModulePrefixes = DefaultOptions.ignoredModulePrefixes,
      pedantic = false,
      parser = cachedParser,
      expandTypeMappings = DefaultOptions.expandTypeMappings
    )

    // Step 2: Convert to Scala.js
    println("Step 2: Converting to Scala.js...")
    val phase2 = new Phase2ToScalaJs(
      pedantic = false,
      scalaVersion = DefaultOptions.versions.scala,
      enableScalaJsDefined = DefaultOptions.enableScalaJsDefined,
      outputPkg = DefaultOptions.outputPackage,
      flavour = DefaultOptions.flavourImpl,
      useDeprecatedModuleNames = DefaultOptions.useDeprecatedModuleNames
    )

    println("Step 3: Applying flavour transformations...")
    val phase3 = new PhaseFlavour(DefaultOptions.flavourImpl, maybePrivateWithin = DefaultOptions.privateWithin)

    println(phase3)

    // Step 4: Create a simple pipeline and run it using PhaseRunner (like SourceOnlyMain)
    println("Step 4: Creating conversion pipeline...")
    val pipeline: RecPhase[LibTsSource, LibScalaJs] = RecPhase[LibTsSource]
      .next(phase1, "typescript")
      .next(phase2, "scala.js")
      .next(phase3, DefaultOptions.flavour.toString)

    val NoListener: PhaseListener[LibTsSource] = (_, _, _) => ()

    println("Step 5: Processing libraries through pipeline...")

    val importedLibs: Map[LibTsSource, PhaseRes[LibTsSource, LibScalaJs]] =
      sources
        .map(s => {
          println(s"DEBUG: Processing library ${s.libName.value}")
          val result = PhaseRunner(pipeline, (_: LibTsSource) => logger.void, NoListener)(s)
          println(s"DEBUG: Result for ${s.libName.value}: ${result.getClass.getSimpleName}")
          (s: LibTsSource) -> result
        })
        .toMap

    println(s"DEBUG: importedLibs contains ${importedLibs.size} entries:")
    importedLibs.foreach { case (source, result) =>
      println(s"DEBUG:   ${source.libName.value} -> ${result.getClass.getSimpleName}")
    }
    println(importedLibs)

    // Step 6: Process results and generate files
    println("Step 6: Processing results and generating Scala files...")
    PhaseRes.sequenceMap(importedLibs.toSorted) match {
      case PhaseRes.Ok(LibScalaJs.Unpack(libs)) =>
        println(libs)
        println(s"✓ Successfully processed ${libs.size} libraries")

        // Step 7: Create global scope for all libraries
        println("Step 7: Creating global scope...")
        val globalScope = new TreeScope.Root(
          DefaultOptions.outputPackage,
          Name.dummy,
          libs.map { case (_, l) => (l.scalaName, l.packageTree) },
          logger.void,
          false
        )

        // Static minimization policy applied to every run
        val minimizes =
          List("quill", "clsx", "scroll-into-view-if-needed").map(TsIdentLibrary.apply)
        val staticMinimize: Selection[TsIdentLibrary] = Selection.AllExcept(minimizes: _*)
        val staticMinimizeKeep: IArray[QualifiedName] = IArray.Empty

        // Compute shared keep index for minimization (only if needed)
        lazy val referencesToKeep: Minimization.KeepIndex = {
          val packagesWithShouldMinimize: IArray[(PackageTree, Boolean)] =
            IArray.fromTraversable(libs).map { case (s, l) => (l.packageTree, staticMinimize(s.libName)) }

          Minimization.findReferences(globalScope, staticMinimizeKeep, packagesWithShouldMinimize)
        }

        // Step 8: Set up minimization (simplified - no minimization for now)
        println("Step 8: Generating source files...")
        val allGeneratedFiles: Iterator[(os.Path, String)] = libs.iterator.flatMap { case (source, lib) =>
          val willMinimize = staticMinimize(source.libName)
          val treeToPrint =
            if (willMinimize) Minimization(globalScope, referencesToKeep, logger.void, lib.packageTree)
            else lib.packageTree

          val scalaFiles = Printer(
            globalScope,
            new ParentsResolver,
            treeToPrint,
            DefaultOptions.outputPackage,
            DefaultOptions.versions.scala
          )

          val targetFolder = sourceOutputDir / source.libName.value
          val minimizedMsg = if (willMinimize) "minimized " else ""
          logger.warn(
            s"Writing ${minimizedMsg}${source.libName.value} (${scalaFiles.length} files) to $targetFolder..."
          )
          scalaFiles.map { case (relPath, content) =>
            (targetFolder / relPath, content)
          }.iterator
        }

        // Step 9: Write all files to disk
        println("Step 9: Writing files to disk...")
        val writtenFiles = allGeneratedFiles.map { case (path, content) =>
          files.softWrite(path) { writer =>
            writer.write(content)
          }
          path
        }.toSet

        println(s"✓ Successfully generated ${writtenFiles.size} Scala source files to $sourceOutputDir")
        0

      case PhaseRes.Failure(errors) =>
        println("✗ Pipeline failed with errors:")
        errors.foreach {
          case (source, Left(throwable)) =>
            println(s"  ${source.libName.value}: ${throwable.getMessage}")
          case (source, Right(message)) =>
            println(s"  ${source.libName.value}: $message")
        }
        1

      case PhaseRes.Ignore() =>
        println("- Pipeline ignored all sources")
        0
    }
  }
}