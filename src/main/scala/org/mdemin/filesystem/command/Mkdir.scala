package org.mdemin.filesystem.command
import org.mdemin.filesystem.State
import org.mdemin.filesystem.file.{DirEntry, Directory}

class Mkdir(name: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.workingDirectory
    if (wd.hasEntry(name)) {
      state.setMessage("Entry " + name + " already exists!")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(name + " must not contain separators!")
    } else if (checkIllegal(name)) {
      state.setMessage(name + ": illegal entry name!")
    } else {
      doMkdir(state, name)
    }
  }


  def doMkdir(state: State, name: String): State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): DirEntry = {
      if (path.isEmpty) {
        currentDirectory.addEntry(newEntry)
      } else {
        val oldEntry = currentDirectory.findEntry(path.head)
        if (oldEntry.isEmpty) throw new RuntimeException(s"${path.head} does not exists in ${currentDirectory.path}")
        currentDirectory.replaceEntry(oldEntry.get.name,
          updateStructure(oldEntry.get.asDirectory, path.tail, newEntry))
      }
    }

    val wd = state.workingDirectory
    val fullPath = wd.path

    val allDirsInPath = wd.allDirectoriesInPath
    val newDir = Directory.empty(fullPath, name)
    val newRoot = updateStructure(state.root, allDirsInPath, newDir)
    val newWorkingDirectory = newRoot.asDirectory.findDescendant(allDirsInPath)
    State(newRoot.asDirectory, newWorkingDirectory)
  }

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }
}

object Mkdir {
  def apply(name: String): Mkdir = new Mkdir(name)
}
