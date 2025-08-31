package org.scalablytyped.converter.cli
import org.scalablytyped.converter.internal.files

class Paths(base: os.Path) {
  lazy val out: os.Path =
    files.existing(base / "out")
  val node_modules: Option[os.Path] =
    Option(base / "node_modules").filter(files.exists)
  val packageJson: Option[os.Path] =
    Option(base / "package.json").filter(files.exists)
}