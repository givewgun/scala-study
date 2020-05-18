package lectures.part1as

object AdvancePatternMatching extends App {

  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"the only elem is $head")
    case _ =>
  }
  /*
    -constant
    -wildcard
    -case classes
    -tuples
    -some special magic !!!
   */

  //not a case class -> how??
  //create singleton objects with unapply(arg: type to match): Option[(val / tuples)]
  class Person(val name: String, val age: Int)

  object Person {
    def unapply(person: Person): Option[(String, Int)] = { //match the whole class
      if (person.age < 12) Some((person.name, person.age))
      else None
    }

    def unapply(age: Int): Option[String] = { //match only age
      Some(if (age < 21) "minor" else "major")
    }
  }

  val bob = new Person("Bob", 12)
  val greeting = bob match {
    case Person(n, a) => s"Hi im $n, Im $a"
  }
  println(greeting)

  val legalStatus = bob.age match { //match only age
    case Person(status) => s"My legal status : $status"
  }

  /*
  Excercise
   */
  val n: Int = 45
  val mathProperty = n match {
    case x if x < 10 => "single digit"
    case x if x % 2 == 0 => "an even  number"
    case _ => "no property"
  }

  //create singleton object for each conditions
  object even {
    def unapply(arg: Int): Boolean = (arg % 2 == 0)
  }

  object singleDigit {
    def unapply(arg: Int): Boolean = (arg < 10)
  }

  val mathProperty2 = n match {
    case singleDigit() => "single digit" //single boolean test
    case even() => "an even  number"
    case _ => "no property"
  }

  //infix pattern
  //only works for 2 things in the pattern
  case class Or[A, B](a: A, b: B) //Either
  val either = Or(2, "two")
  val humanDescription = either match {
    case number Or string => s"$number is $string"
  }
  println(humanDescription)

  //decomposing sequences
  val vararg = numbers match {
    case List(1, _*) => "starting with 1"
  }
  //own collection
  //unapplySequence is actually like unapply but on a sequence / list / ....
  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }
  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] = {
      if (list.equals(Empty)) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
    }
  }

  val myList: MyList[Int] = new Cons(1, Cons(2, Cons(3, Empty)))
  val decompose = myList match {
    case MyList(1, 2, _*) => "Starting with 1and 2"
    case _ => "something else"
  }

  //custom return type for unapply
  //isEmpty: Boolean, get: Something
  //unapply can return anything not limited to Options as long as
  //it has isEmpty and get Method!

  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }
  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false
      override def get: String = person.name
    }
  }

  println(bob match {
    case PersonWrapper(n) => s"This person name is $n"
    case _ => "what"
  })







}
