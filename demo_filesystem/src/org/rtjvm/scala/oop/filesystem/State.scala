package org.rtjvm.scala.oop.filesystem

import org.rtjvm.scala.oop.files.Directory

//state of the world -> root , directory

//root, working directory, output from prev commands
class State(val root: Directory, val wd: Directory, val output: String) {
  
  def show = {
    println(output) //message
    print(State.SHELL_TOKEN + " ")
  }

  //immutability
  def setMessage(message: String): State = {
    State(root, wd, message)
  }


}
object State{
  val SHELL_TOKEN = "$"
  
  //factory method -> apply
  def apply(root: Directory, wd: Directory, output: String = ""): State = new State(root, wd, output)
}
