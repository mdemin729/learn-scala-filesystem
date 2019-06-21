package org.mdemin.filesystem.file

abstract class DirEntry(val parentPath: String, val name: String) {
  def asDirectory: Directory = ???

  def path: String = parentPath +
    (if (parentPath.length == 1) "" else Directory.SEPARATOR) +
    name
}
