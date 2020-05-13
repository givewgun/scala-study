package org.rtjvm.scala.oop.commands

import org.rtjvm.scala.oop.filesystem.State

//like a transformer -> function
trait Command extends {

  def apply(state: State): State

}

object Command{
  val MKDIR = "mkdir"
  val LS = "ls"
  val PWD = "pwd"
  val TOUCH = "touch"
  val CD = "cd"
  val RM = "rm"
  val ECHO = "echo"
  val CAT = "cat"


  def emptyCommand: Command = new Command {
    override def apply(state: State): State = state
  }
  def incompleteCommand(name: String): Command = new Command {
    override def apply(state: State): State = {
      state.setMessage(name + ": incomplete command")
    }
  }

  //factory method -> maybe match?
  def from(input: String): Command = {

    val tokens = input.split(" ") //array of Strings cmd args1 args2 ...
    if(input.isEmpty || tokens.isEmpty) emptyCommand //input.isEmpty when not type anything at all
    else if(MKDIR.equals(tokens(0))){ //should use pattern matching
      if(tokens.length < 2) incompleteCommand(MKDIR)
      else new Mkdir(tokens(1))
    }else if(LS.equals(tokens(0))){
      new Ls
    }else if(PWD.equals(tokens(0))){
      new Pwd
    }else if(TOUCH.equals(tokens(0))){
      new Touch(tokens(1))
    }else if(CD.equals(tokens(0))) {
      new Cd(tokens(1))
    }else if(RM.equals(tokens(0))){
      if(tokens.length < 2) incompleteCommand(RM)
      else new Rm(tokens(1))
    }else if(ECHO.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(ECHO)
      else new Echo(tokens.tail)
    }else if(CAT.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(CAT)
      else new Cat(tokens(1))
    }else new UnknownCommand
  }
}
