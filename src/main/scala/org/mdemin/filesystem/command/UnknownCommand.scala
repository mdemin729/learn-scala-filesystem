package org.mdemin.filesystem.command
import org.mdemin.filesystem.State

class UnknownCommand extends Command {

  override def apply(state: State): State =
    state.setMessage("Unknown command")

}
