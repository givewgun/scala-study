package org.rtjvm.scala.oop.commands
import org.rtjvm.scala.oop.files.Directory
import org.rtjvm.scala.oop.filesystem.State

class Rm(name: String) extends Command {

  def doRm(state: State, path: String): State ={

    /*
      /a => ["a"]
        => isPathEmpty?
        => new root without the folder a

      /a/b => ["a","b"]
        => isPathEmpty?
        =>?
        => nextDirectory = /a //call rmHelper
        =>rmHelper(/a, ["b"])
          => isPathEmpty?
          => new /a (remove folder b)
          => replace("a", new /a) = new root
     */

    def rmHelper(currentDirectory: Directory, path: List[String]): Directory ={
      if(path.isEmpty) currentDirectory
      else if(path.tail.isEmpty) currentDirectory.removeEntry(path.head)
      else{
        //find next dir
        val nextDirectory = currentDirectory.findEntry(path.head)
        if(!nextDirectory.isDirectory) currentDirectory
        else {
          val newNextDirectory = rmHelper(nextDirectory.asDirectory, path.tail)
          if(newNextDirectory == nextDirectory) currentDirectory
          else currentDirectory.replaceEntry(path.head, newNextDirectory)
        }
      }
    }

    //4. find the entry to rm
    //5. update structure like mkkdir

    val tokens = path.substring(1).split(Directory.SEPARATOR).toList //abs path split
    val newRoot: Directory = rmHelper(state.root, tokens) //oldroot->newroot
    //check if newRoot == oldRoot
    if(newRoot == state.root){
      state.setMessage(path + ": no such file or directory")
    }else{
      //factory apply method of companion object
      State(newRoot, newRoot.findDescendant(state.wd.path.substring(1)))//state.wd.path = relative path of the new root
    }
  }

  override def apply(state: State): State = {
    //1. get working directory
    val wd = state.wd

    //2. get absolute path of file to rm
    val absolutePath = {
      if(name.startsWith(Directory.SEPARATOR)) name
      else if(wd.isRoot) wd.path + name // /name
      else wd.path + Directory.SEPARATOR + name
    }

    //3. check like rm /
    if(absolutePath.equals(Directory.ROOT_PATH)){
      state.setMessage("CANT DELETE ROOT")
    }else{
      doRm(state, absolutePath)
    }
  }
}
