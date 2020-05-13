package org.rtjvm.scala.oop.commands

import org.rtjvm.scala.oop.files.{DirEntry, Directory}
import org.rtjvm.scala.oop.filesystem.State

abstract class CreateEntry(name: String) extends Command {

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }

  //DirEntry cuz Not only Directory -> DirEntry is the super
  //! try to make name&type general as possible
  //new root
  def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
    if(path.isEmpty) currentDirectory.addEntry(newEntry)
    else{
      //find next decendant -> to update then fallback....
      val oldEntry = currentDirectory.findEntry(path.head).asDirectory
      currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))  //replace old entry with new same oldentry + new entry in contents
      //will actually replace at the end then replace fallback to root
      //find head of the path
      /*
        /a/b
            (contents)
            (new entry) /e

         new root = updateStructure(root, ["a", "b"], /e =  root.replaceEntry("a", updateStructure(/a, ["b], /e) = /a.replaceEntry("b", updateStructure(/b, [], /e) = /b.add(/e))))
          => path.isEmpty?
          => oldentry = /a
          root.replaceEntry("a", updateStructure(/a, ["b"], /e) = /a.replaceEntry("b", updateStructure(/b, [], /e) = /b.add(/e)))
            => path.isEmpty?
            => oldEntry = /b
            /a.replaceEntry("b", updateStructure(/b, [], /e) = /b.add(/e))
)
            => path.isEmpty? => /b.add(/e)


       */
    }

    /*
      /a/b
          /c
          /d
          new (/e)

       = new /a (cuz List[DirEntry} has changed
         ->new /b (parent /a)folder
                /c
                /d
                /e added
     */



  }

  def doCreateEntry(state: State, name: String): State = {
    val wd = state.wd
    val fullPath = wd.path

    //1. all the directories in the full path
    val allDirsInPath = wd.getAllFoldersInPath

    //2. crate new directory entry in wd
    // TODO implement this
    val newEntry: DirEntry = createSpecificEntry(state)

    //3. update the whole directory structure starting from the root
    // (the directory structure is immutable)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)

    //4. find new working directory INSTANCE given wd's full path in the NEW directory Structure
    //cuz allDirsInPath is the original working directory (traversal history root->a->b! )
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)
  }

  def createSpecificEntry(state: State): DirEntry

  override def apply(state: State): State = {
    val wd = state.wd
    if(wd.hasEntry(name)){
      state.setMessage("Entry: " + name + " already existed")
    }
    else if(name.contains(Directory.SEPARATOR)){
      //mkdir something/something NOT ALLOW -> add -p
      state.setMessage(name + " Must not contains seperators!")
    }
    else if(checkIllegal(name)){
      state.setMessage(name + " : illegal entry name")
    }
    else{
      doCreateEntry(state, name)
    }

  }
}
