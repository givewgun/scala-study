package lectures.part4PatternMatching

import exercise.part3.{Cons, EmptyList, MyList}

object AllThePatterns extends App {

  //1. constants
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a Number"
    case "Scala" => "The scala"
    case true => "Truth"
    case  AllThePatterns => "A singleton Object" //can match singleton object
  }

  //2. mathc anything
  //2.1 wildcard
  val matchAnything = x match {
    case _ =>
  }
  //2.2 variable
  val matchVariable = x match {
    case something => s"....$something" //like previous ex
  }

  //3 tuples
  val aTuple = (1,2)
  val matchTuple = x match {
    case (1, 1) =>
    case (something, 2) => s"....$something"
  }

  val nestedTuple = (1, (2, 3))
  val matchNestedTuple = x match {
    case (_ , (2, v)) => s"....$v"
  }
  //PM can be nested

  //4 case classes - constructor pattern
  val aList: MyList[Int] = Cons(1,Cons(2, EmptyList))
  val aMatchList = aList match {
    case EmptyList =>
    case Cons(head, tail) => //1, Cons(2,Empty)
    case Cons(head, Cons(subhead, subtail)) => //1, 2,Empty
  }

  //5. list pattern
  val aStandardList = List(1,2,3,42)
  val standardListMatching = aStandardList match {
    case List(1, _, _, _, _) => //extractor as List is not a case class but the constructor allows it
    case List(1, _*) => //list of arbitary
    case 1:: List(_) => //infix pattern
    case List(1,2,3) :+ 42 => //infix
  }

  //6. type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => //explicit type specifier
  }

  //7.name binding
  val nameBindingMatch = aList match {
    case notEmptyList @ Cons(_, _) => //name a pattern => use the name later(here)
    case Cons(1, rest @ Cons(2,_)) => //name binding inside nested patterns
  }

  //8 multi pattern
  val multiPattern = aList match {
    case EmptyList | Cons(0, _) => //compound pattern
  }

  // if guards
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _)) if specialElement % 2 == 0 =>
  }

  /*
  Question
   */
  val numbers = List(1,2,3)
  val numberMatch = numbers match {
    case listOfStrings: List[String] => "string list"
    case listOfInt: List[String] => "number list"
    case _ => ""
  }

  println(numberMatch) // string list
  /*
  WHY???
  -JVM fault cuz backword compatibility => afterType checking, our pattern match type are erased
  TYPE ERASURE!!!
   */

}
