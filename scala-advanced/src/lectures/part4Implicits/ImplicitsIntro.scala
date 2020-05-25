package lectures.part4Implicits

object ImplicitsIntro extends App {

  val pair = "Gun" -> "555"
  val intPair = 1 -> 2

  case class Person(name: String) {
    def greet = s"Hi I'm $name"

  }

  implicit def fromStringToPerson(str: String): Person = Person(str)

  //by using implicit
  println("Peter".greet) //compiler looks for anyimplicits that has greet method
  //println(fromStringToPerson("Peter").greet)

//  class A {
//    def greet: Int = 2
//  }
//  implicit def fromStringToA(str: String): A = new A

  //implicit parameter
  def increment(x: Int)(implicit amount: Int) = x + amount
  implicit val defaultAmount = 10

  //DIFFERENT FROM DEFAULT PARAMETER!! -> compiler find it for us
  increment(2)

}
