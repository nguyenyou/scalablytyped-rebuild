package org.scalablytyped.converter.cli

import org.scalablytyped.converter.internal.importer.{Bootstrap, ConversionOptions, LibTsSource, PersistingParser}
import org.scalablytyped.converter.internal.scalajs.Name
import org.scalablytyped.converter.internal.ts.{PackageJson, TsIdentLibrary}
import org.scalablytyped.converter.internal.{InFolder, Json, constants, files}
import org.scalablytyped.converter.internal.logging._

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
    ignored = SortedSet("typescript"),
    stdLibs = SortedSet("es6"),
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
    
    0
  }
}