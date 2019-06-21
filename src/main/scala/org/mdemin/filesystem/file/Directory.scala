package org.mdemin.filesystem.file

import scala.annotation.tailrec

case class Directory(override val parentPath: String,
                     override val name: String,
                     contents: List[DirEntry]) extends DirEntry(parentPath, name) {
  def addEntry(newEntry: DirEntry): DirEntry =
    new Directory(parentPath, name, contents :+ newEntry)

  def replaceEntry(name: String, newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents.filter(e => !e.name.equals(newEntry.name)) :+ newEntry)

  def findEntry(entryName: String): Option[DirEntry] = contents.find(dirEntry => dirEntry.name.equals(entryName))

  def findDescendant(allDirsInPath: List[String]): Directory = {
    @tailrec
    def recurse(currentDir: Directory, allDirsInPath: List[String]): Directory = {
      if (allDirsInPath.isEmpty)
        return currentDir

      val entryName = allDirsInPath.head
      val maybeEntry = contents.find(dirEntry => dirEntry.name.equals(entryName))
      if (maybeEntry.isEmpty)
        throw new RuntimeException(s"Entry $entryName is missing!")
      val nextLevelDir = maybeEntry.get.asInstanceOf[Directory]
      recurse(nextLevelDir, allDirsInPath.drop(1))
    }

    recurse(currentDir = this, allDirsInPath)
  }

  def hasEntry(name: String): Boolean = contents.exists(dir => dir.name.equals(name))

  def allDirectoriesInPath(): List[String] =
    path.split(Directory.SEPARATOR).toList.drop(1)

  override def asDirectory: Directory = this
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")

  def empty(parentPath: String, name: String) =
    new Directory(parentPath, name, List())
}