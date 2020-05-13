package lectures.part2oop

import exercise.part2.Writer
import playground.{PrinceCharming, Cinder => SoulOfCinder} // => to change import name (aliasing)

object PackagingAndImports extends App {

  val writer = new Writer("Gun","FJHF", 11)

  val person = new lectures.part2oop.Person("sdfad") // fully qualified name

  //packages are in hierarchy
  //matching folder structures

  // package object
  // universal things

  sayHello

  val prince = new PrinceCharming
  val soul = new SoulOfCinder

  //default imports




}
