package org.mdemin.filesystem.command
import org.mdemin.filesystem.State

class Ls(name: String) extends Command {

  override def apply(state: State): State = {
    state.workingDirectory.contents.foreach(dirEntry => {
      println(s"[${dirEntry.getClass.getSimpleName}] ${dirEntry.name}")
    })
    state
  }
}

object Ls {
  def apply(name: String): Ls = new Ls(name)
}