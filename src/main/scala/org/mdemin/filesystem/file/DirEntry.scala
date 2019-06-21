package org.mdemin.filesystem.file

abstract class DirEntry(val parentPath: String, val name: String) {
  def asDirectory: Directory = ???

  def path = parentPath + Directory.SEPARATOR + name
}
