package org.scalablytyped.converter.cli

import org.scalablytyped.converter.internal.Json
import org.scalablytyped.converter.internal.ts.PackageJson

object Tracing {
  private val inDirectory = os.pwd
  lazy val paths = new Paths(inDirectory)

  def main(args: Array[String]): Unit = System.exit(mainNoExit(args))

  def mainNoExit(args: Array[String]): Int = {
    val packageJsonPath = paths.packageJson.getOrElse(sys.error(s"${inDirectory} does not contain package.json"))
    val nodeModulesPath = paths.node_modules.getOrElse(sys.error(s"${inDirectory} does not contain node_modules"))
    val packageJson = Json.force[PackageJson](packageJsonPath)
    println(packageJson)
    
    println(packageJsonPath)
    println(nodeModulesPath)
    println("Hello, world!")
    0
  }
}