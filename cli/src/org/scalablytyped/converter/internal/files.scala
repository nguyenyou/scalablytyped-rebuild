package org.scalablytyped.converter.internal

object files {
  def exists(path: os.Path): Boolean = path.toIO.exists()
  def existing(p: os.Path): os.Path = {
    os.makeDir.all(p)
    p
  }
}