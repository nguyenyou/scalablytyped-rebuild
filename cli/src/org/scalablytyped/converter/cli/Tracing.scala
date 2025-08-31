package org.scalablytyped.converter.cli

import org.scalablytyped.converter.{Flavour, Selection}
import org.scalablytyped.converter.internal.importer.{Bootstrap, ConversionOptions, EnabledTypeMappingExpansion, LibScalaJs, LibTsSource, PersistingParser, Phase1ReadTypescript, Phase2ToScalaJs, PhaseFlavour}
import org.scalablytyped.converter.internal.scalajs.{Name, Versions}
import org.scalablytyped.converter.internal.ts.{PackageJson, TsIdentLibrary}
import org.scalablytyped.converter.internal.{InFolder, Json, constants, files}
import org.scalablytyped.converter.internal.logging._
import org.scalablytyped.converter.internal.phases.RecPhase
import org.scalablytyped.converter.internal.ts.CalculateLibraryVersion.PackageJsonOnly

import scala.collection.immutable.SortedSet

object Tracing {
  private val inDirectory = os.pwd
  lazy val paths = new Paths(inDirectory)
  val parseCachePath = Some(files.existing(constants.defaultCacheFolder / 'parse).toNIO)

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
      require(fromPackageJson.nonEmpty, "No libraries found in package.json")
      val ret = fromPackageJson -- DefaultOptions.ignoredLibs
      require(ret.nonEmpty, s"All libraries in package.json ignored")
      ret
    }

    val bootstrapped: Bootstrap.Bootstrapped = Bootstrap.fromNodeModules(InFolder(nodeModulesPath), DefaultOptions, wantedLibs)
    println(bootstrapped)

    val sources: Vector[LibTsSource] = {
      bootstrapped.initialLibs match {
        case Left(unresolved) => sys.error(unresolved.msg)
        case Right(initial)   => initial
      }
    }

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
    
    0
  }
}