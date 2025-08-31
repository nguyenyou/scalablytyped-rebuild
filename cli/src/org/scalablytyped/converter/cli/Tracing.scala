package org.scalablytyped.converter.cli

object Tracing {
  def main(args: Array[String]): Unit = System.exit(mainNoExit(args))

  def mainNoExit(args: Array[String]): Int = {
    println("Hello, world!")
    0
  }
}
