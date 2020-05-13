package org.rtjvm.scala.oop.commands

import javax.naming.spi.DirStateFactory.Result
import org.rtjvm.scala.oop.files.{DirEntry, Directory}
import org.rtjvm.scala.oop.filesystem.State

import scala.annotation.tailrec

class Cd(dirName: String) extends Command {


  def doFindEntry(root: Directory, path: String): DirEntry = { //path is absolutepath

    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
      if(path.isEmpty || path.head.isEmpty) currentDirectory //path.head.isEmpty is cd / case -> split and the list is [""]
      else if(path.tail.isEmpty) currentDirectory.findEntry(path.head) //only one arg cd /a
      else{
        val nextDir = currentDirectory.findEntry(path.head)
        if(nextDir == null || !nextDir.isDirectory) null //no such directory
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }

    //1. tokens
    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList

    //1.5 eliminate/collapse relative tokens
    /*
      /a/. => ["a", "."] => ["a"]// . standalone don't have effect on absolute path
      /a/.. => ["a", ".."] => []// .. move to parent-> collapse
     */
    @tailrec
    def collapseRelativeTokens(path: List[String], result: List[String]): List[String] ={ //result ~ accumulator
      /*
        /a/b = ["a", "b"]
          => path.isEmpty?
          => collapseRelativeTokens(["b"], result = []:+ "a' = ["a"])
            => path.isEmpty?
            => cRT([], result = ["a"] :+ ["b"] = ["a","b"]

         /a/.. = ["a", ".."]
          => path.isEmpty?
          => collapseRelativeTokens([".."], result = [] :+ "a' = ["a"])
            => path.isEmpty?
            =>"..".equals(path.head)
              => cRT([], result = [])

         /a/b/c/.. = ["a", "b", "c", ".."]
          => path.isEmpty?
          => collapseRelativeTokens(["b", "c", ".."], result = []:+ "a' = ["a"])
            => path.isEmpty?
            => cRT(["c", ".."], result = ["a"] :+ ["b"] = ["a","b"])
              => path.isEmpty?
              => cRT([ ".."], result = ["a", "b"] :+ ["c"] = ["a","b", "c"])
                => path.isEmpty?
                =>"..".equals(path.head)
                  => cRT([], result = ["a", "b"])

       */
      if (path.isEmpty) result
      else if(".".equals(path.head)) collapseRelativeTokens(path.tail, result)
      else if("..".equals(path.head)){
        //guard case of root /../../../.. .....
        if(result.isEmpty) null
        else collapseRelativeTokens(path.tail, result.init) // !!!list.init list all elem not the last
      }else{
        collapseRelativeTokens(path.tail, result :+ path.head)
      }
    }

    val newTokens = collapseRelativeTokens(tokens, List())

    //2. navigate to the correct entry (aux fuc)
    if(newTokens == null) null
    else findEntryHelper(root, newTokens)
  }

  override def apply(state: State): State = {
    //magic
    //wd change if found, state.root same
    /*
    cd /something/something/.../
    cd a/b/c = relative path to the working directory(wd)

    cd ..
    cd .
    cd a/./../.
     */

    //1. find the root
    val root = state.root
    val wd = state.wd

    //2.find the absolute path of the directory that i want to cd to
    val absolutePath = {
      if(dirName.startsWith(Directory.SEPARATOR)) dirName
      else if(wd.isRoot) wd.path + dirName //if wd = / nee to check first else => //a/b/c
      else wd.path + Directory.SEPARATOR + dirName
    }

    //3.find the directory to cd to, given the path
    val destinationDirectory = doFindEntry(root, absolutePath)

    //4. change the state wd to current directory
    //if destinationDir == file -> fail
    if(destinationDirectory == null || !destinationDirectory.isDirectory) state.setMessage(dirName + ": no such directory")
    else {
      State(root, destinationDirectory.asDirectory)
    }


  }
}
