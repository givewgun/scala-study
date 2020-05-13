package org.rtjvm.scala.oop.filesystem

import java.util.Scanner

import org.rtjvm.scala.oop.commands.Command
import org.rtjvm.scala.oop.files.Directory

object FileSystem extends App {

  val root = Directory.ROOT

  //infinite collections
  // [1,2,3,4]
  /*
  foldLeft
  0 is begin state
  0 (op) 1 => 1
  1 (op) 2 => 3
  3 (op) 3 => 6
  6 (op) 4 => 10 (your last value)

  List(1,2,3,4).foldLeft(0)((x,y) => x + y) ~ reduce
   */
  io.Source.stdin.getLines().foldLeft(State(root, root))((currentState, newLine) => {
    currentState.show
    Command.from(newLine).apply(currentState) //new state
  })

//  var state = State(root, root)
//  val scanner = new Scanner(System.in)
//
//  while(true){
//    state.show
//    val input = scanner.nextLine()
//    state = Command.from(input).apply(state) //var cuz state will constantly change -> func proc can eliminate it
//  }


}
