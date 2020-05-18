package lectures.part1as

import scala.annotation.tailrec

object Recap extends App {

  val aCondition: Boolean = false
  val aConditionVal = if (aCondition) 42 else 45
  //instruction vs expression
  //-instruction -> imperative -> sequence
  //-expression -> functionla -> block -> last expression

  //compiler check type for us
  val aCodeBlock = {
    if (aCondition) 54
    else 56
  }

  // Unit ~ void
  val unit: Unit = println("SHDSJD")

  //function
  def f(x: Int): Int = x + 1
  val g = (x: Int) => x + "dad"

  //recursion stack frame and tailrec
  @tailrec
  def factorial(n: Int, acc: Int): Int = {
    if (n <= 0) acc
    else factorial(n - 1, n * acc)
  }

  //OO programming
  class Animal
  class Dog extends Animal

  //ploymorphism
  val aDog: Animal = new Dog //subtypes

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Croc extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("crunch")
  }

  //method notations
  val aCroc = new Croc
  aCroc.eat(aDog)
  aCroc eat aDog // Natural language - infix

  //op is method
  1 + 2
  1.+(2)

  //anonymous classes
  //new anonymous class extends Carnivore and override its method
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("DDDDDDDDDd")
  }

  //generics
  abstract class MyList[+A] //variance
  //singleton and companions
  object MyList

  //case classes -> already serializable
  case class Person(name: String, age: Int)

  //exceptions and try catch
  val throwsExceptions = throw new RuntimeException //Nothing
  val aPotentialFailure = try {
    throw new RuntimeException
  } catch {
    case e: Exception => "caught exceptions"
  } finally {
    println("some log")
  }

  //packaging & import

  //functional programming
  //apply( ) method -> call
  val incrementor = new Function[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  incrementor(1)

  val anonymousIncrementor = (x:Int) => x + 1
  List(1,2,3).map(anonymousIncrementor) //HOF

  //map flatap filter

  //for comprehension
  val pairs = for {
    num <- List(1,2,3)
    char <- List('a','b','c')
  } yield num + " " + char

  //Collections : Seqs, Arrays, Lists, Vectors, Maps, Tuples
  val aMap = Map(
    "Gun" -> 15515
  )

  //"collections" Option & Try
  val anOption = Some(2)
  val i = anOption match {
    case Some(i) => i
    case _ => None
  }

  //pattern matching
  val x = 2
  val order = x match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => "Whatever"
  }

  val bob = Person("bob", 12)
  val greetings = bob match {
    case Person(n, _) => s"Hi Im $n"
  }




}
