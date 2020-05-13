package org.rtjvm.scala.oop.files

import org.rtjvm.scala.oop.filesystem.FileSystemException

import scala.annotation.tailrec

class Directory(override val parentPath: String,
                override val name: String,
                val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {

  def isRoot: Boolean = parentPath.isEmpty


  def findEntry(entryName: String): DirEntry = {

    @tailrec
    def findEntryHelper(name: String, contentList: List[DirEntry]): DirEntry = {
      if(contentList.isEmpty) null
      else if(contentList.head.name.equals(name)) contentList.head
      else findEntryHelper(name, contentList.tail)
    }

    findEntryHelper(entryName, contents)
  }

  def hasEntry(name: String): Boolean = findEntry(name) != null

  def addEntry(newEntry: DirEntry): Directory = {
    new Directory(parentPath, name, contents :+ newEntry)
  }

  def replaceEntry(entryName: String, newEntry: Directory): Directory = {
    new Directory(parentPath, name, contents.filter(e => !e.name.equals(entryName)) :+ newEntry)
  }

  def findDescendant(path: List[String]): Directory = {
    if(path.isEmpty) this //no descendent to search for
    else findEntry(path.head).asDirectory.findDescendant(path.tail)
  }


  def getAllFoldersInPath: List[String] = { //all predecessor folder + self !!NOT ALL CHILD FOLDER
    path.substring(1).split(Directory.SEPARATOR).toList.filter(f => !f.isEmpty) //filter out empty string (like from root)
    // /a/b/c/d => List["a", "b","c","d"]

  }



  override def asDirectory: Directory = this

  override def getType: String = "Directory"

  override def asFile: File = throw new FileSystemException("A directory cannot be converted to a file")

  override def isDirectory: Boolean = true
  override def isFile: Boolean = false
}

//instantiate by calling method not direct constructor of a class
//companion object
object Directory{
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def empty(parentPath: String, name: String): Directory ={
    new Directory(parentPath, name, List())
  }

  def ROOT: Directory =  Directory.empty("","")
}
