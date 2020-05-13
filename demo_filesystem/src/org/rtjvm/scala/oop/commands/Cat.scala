package org.rtjvm.scala.oop.commands
import org.rtjvm.scala.oop.filesystem.State

class Cat(fileName: String) extends Command {
  //not support abs / rel path yet...
  override def apply(state: State): State = {
    val wd = state.wd

    val dirEntry = wd.findEntry(fileName)
    if(dirEntry == null || !dirEntry.isFile)
      state.setMessage(fileName + ": No such file")
    else
      state.setMessage(dirEntry.asFile.contents)
  }
}
