package org.mdemin.filesystem.command

import org.mdemin.filesystem.State
import org.mdemin.filesystem.file.Directory

class Cd(newPath: String) extends Command {

  override def apply(state: State): State = {
    val dirsInPath = newPath.split("/").toList
    val newWorkingDirectory: Directory =
      if (dirsInPath.isEmpty) {
        state.root
      } else {
        if (dirsInPath.head.isEmpty)
          state.root.findDescendant(dirsInPath.tail)
        else
          state.workingDirectory.findDescendant(dirsInPath)
      }

    State(state.root, newWorkingDirectory)
  }
}

object Cd {
  def apply(newPath: String) = new Cd(newPath)
}