package lectures.part2afp

object LazyEvaluation extends App {

  lazy val x: Int = throw new RuntimeException

  //lazy delays the evaluation of values
  //x evaluate when call
  //println(x) //crashed

  lazy val y: Int = {
    println("hello")
    12
  }
  println(x) //hello 42
  println(x) //42 (evaluate only onced for the first time)

  // examples of implications
  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }
  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition
  println(if (simpleCondition && lazyCondition) "yes" else "no") //no !!! no Boo
  //lazy only evaluated when need (the above is short circuit)

  //in conjunction with call by name
  def byName(n : => Int): Int = {
    lazy val t = n //evaluate once (CALL BY NEED)
    t + t + t + 1
  }
  def retrieveMagicValue = {
    println("wait")
    Thread.sleep(1000)
    42
  }
  println(byName(retrieveMagicValue)) // waiting waiting waiting 127
  // use lazy val

  //CALL BY NEED

  //filtering with lazy val
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30")
    i < 30
  }

  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater than 20")
    i > 20
  }

  val numbers = List(1, 25, 40, 5, 23)
  val lt30 = numbers.filter(lessThan30) //List(1, 25, 5, 23)
  val gt20 = numbers.filter(greaterThan20)

  val lt30lazy = numbers.withFilter(lessThan30) //withFilter use lazy val under the hood
  val gt20lazy = numbers.withFilter(greaterThan20)
  println(gt20lazy) // Travsersable .... !!!NO side efftect -> not calling the method yet
  gt20lazy.foreach(println) //side effect! call!

  //for comprehension use withFilter with guards
  for {
    a <- List(1, 2, 3) if a % 2 == 0
  } yield a + 1
  //is
  List(1, 2, 3).withFilter(_ % 2 == 0).map(_ + 1)

}
