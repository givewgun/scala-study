package lectures.part1as

import scala.util.Try

object DarkSugars extends App {

  //synstax sugar #1 -method with single param
  def singleArgMethod(arg: String): String = s"dsgksdg $arg"

  val description = singleArgMethod{
    //write some complex  code
    "dff"
  }

  val aTryInstance = Try { //~ java try { .. }
    throw new RuntimeException
  }

  List(1,2,3).map { x =>
    x + 1
  }

  //#2 : single abstract method pattern
  trait Action {
    def act(x: Int): Int
  }

  val anInstance: Action = new Action {
    override def act(x: Int): Int = x + 2
  }
  //====
  val lambdaInstance: Action = (x: Int) => x + 2 //magic that it knows that anonymous extends trait
  //example : Runnables
  val thread = new Thread(new Runnable {
    override def run(): Unit = println("DDDDD")
  })
  val aSweeterThread = new Thread( () => println("DDDDD"))

  abstract class AnAbstractType {
    def implemented(x: Int): Int = 23
    def f(a: Int): Unit
  }
  val anAbstractInstance: AnAbstractType = (a: Int) => println("Sweet") //auto override f

  //#3: the :: and #:: methods are special

  val perpendedList = 2 :: List(2,3)
  // 2.::(List(3,4))
  //List(3,4).::(2)
  //???

  //scala spec: last char decides associativity of method
  //if it ends in : -> right associative :(:)
  1 :: 2 :: 3 :: List(4,5)
  List(4,5).::(3).::(2).::(1)

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this //actula impl
  }
  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  //#4 : multiword method naming

  class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s"$name $gossip")
  }
  val lily = new TeenGirl("lily")
  lily  `and then said` "Scala is so sweet"

  //#5 : infix types
  class Composite[A, B]
  //val composite: Composite[Int, String] = ???
  val composite: Int Composite String = ???

  class -->[A, B]
  val towards: Int --> String = ???

  //#6 : update() is very special, much like apply()
  val anArray = Array(1,2,3)
  anArray(2) = 7 // rewritten to anArray.update(2, 7) //idx, val
  //used in mutable collections
  //remember apply() nad update()

  //#7 : setter for mutable containers
  class Mutable {
    private var internalMember: Int = 0 //private for OO encapsulation
    def member = internalMember // getter
    def member_=(value: Int): Unit = {
      internalMember = value // setter
    }
  }

  val mutableContainer = new Mutable
  mutableContainer.member = 42 // rewritten as mutableContainer.member_=(42)







}
