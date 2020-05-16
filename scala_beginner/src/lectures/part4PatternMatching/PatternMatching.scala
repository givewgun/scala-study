package lectures.part4PatternMatching

import scala.util.Random

object PatternMatching extends App{

  //switch on steroid
  val random = new Random()
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "case one"
    case 2 => "case 2"
    case 3 => "case 3"
    case _ => "something else" //wildcard (other csae)
  }

  println(x)
  println(description)

  //1. Decompose val
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 22)

  val greeting = bob match {
    case Person(n,a) if a < 21 => s"Hi my name is $n, age $a. I cant drink" //extract case class val with guard
    case Person(n,a) => s"Hi my name is $n, age $a" //extract case class val
    case _ => "who am I"
  }

  /*
    1. cases match in order
    2. what if no case match (no wildcard) -> scala.MatchError !!!!
    3. type of pattern matching expression = all cases type unified (String, Int) will become Any
   */

  //PM on sealed hierachy -> warning
  sealed class Animal //in only one file
  case class Dog(breed: String) extends Animal
  case class Parrot(breed: String) extends Animal

  val animal: Animal = Dog("Terraa")
  animal match {
    case Dog(someBreed) => println(s"matched a dog of $someBreed")
  }

  // should we match everything?
  //No just go with normal
  val isEven = x match {
    case n if n % 2 == 0 => true
    case _ => false
  }
  //WHY?
  val isEvenCond = if (x % 2 == 0) true else false
  //Just this
  val isEvenNormal = x % 2 == 0



}
