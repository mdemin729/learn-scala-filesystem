package org.mdemin.filesystem

import java.util.{NoSuchElementException, Scanner}

import org.mdemin.filesystem.command.Command
import org.mdemin.filesystem.file.Directory

object Filesystem extends App {

  val root = Directory.ROOT
  var state = State(root, root)
  val scanner = new Scanner(System.in)

  var terminated = false
  while (!terminated) {
    state.show()
    try {
      val input = scanner.nextLine()
      state = Command.from(input).apply(state)
    } catch {
      case _: NoSuchElementException => terminated = true;
    }
  }
}
