package org.mdemin.filesystem

import java.util.Scanner

import org.mdemin.filesystem.command.Command
import org.mdemin.filesystem.file.Directory

object Filesystem extends App {

  val root = Directory.ROOT
  var state = State(root, root)
  val scanner = new Scanner(System.in)

  while (true) {
    state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }
}
