package org.mdemin.filesystem.command
import org.mdemin.filesystem.State

class Pwd extends Command {

  override def apply(state: State): State =
    state.setMessage(state.workingDirectory.path)
}

object Pwd {
  def apply() = new Pwd()
}
