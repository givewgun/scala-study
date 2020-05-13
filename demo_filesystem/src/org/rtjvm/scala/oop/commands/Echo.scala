package org.rtjvm.scala.oop.commands
import org.rtjvm.scala.oop.files.{Directory, File}
import org.rtjvm.scala.oop.filesystem.State

import scala.annotation.tailrec

class Echo(args: Array[String]) extends Command {

  override def apply(state: State): State = {
    /*
      if no args -> same state
      1 arg => print to console
      mul args -> check op
        op = next to last arg
        if >
          echo to file (crete if file not there)
        if >>
          append to file
     */
    if(args.isEmpty) state
    else if(args.length == 1) state.setMessage(args(0))
    else{
      val operator = args(args.length - 2)
      val filename = args(args.length - 1)
      val contents = createContent(args, args.length - 2) // before ops

      if(operator.equals(">>")) {
        doEcho(state, contents, filename, append = true)
      }
      else if(operator.equals(">")) {
        doEcho(state, contents, filename, append = false)
      }
      else {
        state.setMessage(createContent(args, args.length))
      }

    }
  }

  //top index non inclusive!!!!
  def createContent(args: Array[String], topIndex: Int): String = {
    @tailrec
    def createContentHelper(currentIndex: Int, accumulator: String): String ={
      if(currentIndex >= topIndex) accumulator
      else createContentHelper(currentIndex + 1, accumulator + " " + args(currentIndex) )
    }

    createContentHelper(0, "")
  }

  //path is path to navigate to
  def getRootAfterEcho(currentDirectory: Directory, path: List[String], contents: String, append: Boolean): Directory = {
    /*
      if path is empty -> fail -> ret currentDirectory
      else if no more things to explore (path.tail.isEmpty
        find the file to crate/add contents to
        if file not found, create file
        else oif the entry is directory -> fail
        else
          replace or append content to the file
          replace the entry with filename of the NEW file
      else
        find next directory to navigate
        call gRAE recursively on that

        if recursive call fail -> fail
        else replace entry with the NEW directory after the recursive call
     */
    if(path.isEmpty) currentDirectory
    else if(path.tail.isEmpty){
      val dirEntry = currentDirectory.findEntry(path.head)
      if(dirEntry == null) {
        currentDirectory.addEntry(new File(currentDirectory.path, path.head, contents))
      }
      else if(dirEntry.isDirectory) currentDirectory //fail
      else{
        if(append) currentDirectory.replaceEntry(path.head, dirEntry.asFile.appendContents(contents))
        else currentDirectory.replaceEntry(path.head, dirEntry.asFile.setContents(contents))
      }
    }
    else{
      val nextDirectory = currentDirectory.findEntry(path.head).asDirectory
      val newNextDirectory = getRootAfterEcho(nextDirectory, path.tail, contents, append)

      if(newNextDirectory == nextDirectory) currentDirectory
      else currentDirectory.replaceEntry(path.head, newNextDirectory)

    }

  }

  def doEcho(state: State, contents: String, filename: String, append:Boolean): State ={
    /*
    1.check filename (no abs path jsut filename!)
    2.run aux method to update entire folder struct cuz new file cuz directory is IMMUTABLE
     */
    if(filename.contains(Directory.SEPARATOR)){
      state.setMessage("FILE NAME MUST NOT CONTAIN SEPARATOR")
    }else{
      val newRoot: Directory = getRootAfterEcho(state.root, state.wd.getAllFoldersInPath :+ filename, contents, append)
      if(newRoot == state.root){
        state.setMessage(filename + ": no such file")// like echo to a folder
      }else{
        State(newRoot, newRoot.findDescendant(state.wd.getAllFoldersInPath))
      }
    }
  }

}
