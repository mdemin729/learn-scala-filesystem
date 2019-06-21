package org.mdemin.filesystem.command

import org.mdemin.filesystem.State

trait Command {

  def apply(state: State): State
}

object Command {
  val MKDIR = "mkdir"
  val LS = "ls"
  val PWD = "pwd"
  val CD = "cd"

  def emptyCommand: Command =
    (state: State) => state.setMessage("")
  def incompleteCommand(name: String): Command =
    (state: State) => state.setMessage(name + ": incomplete command!")

  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")

    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else {
      if (MKDIR.equals(tokens(0))) {
        if (tokens.length < 2) incompleteCommand(MKDIR)
        else Mkdir(tokens(1))
      }
      else if (LS.equals(tokens(0))) {
        Ls(tokens(0))
      } else if (PWD.equals(tokens(0))) {
        Pwd()
      } else if (CD.equals(tokens(0))) {
        if (tokens.length < 2) incompleteCommand(CD)
        else Cd(tokens(1))
      }
      else new UnknownCommand
    }
  }
}