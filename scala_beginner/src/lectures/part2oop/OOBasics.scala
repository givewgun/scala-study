package lectures.part2oop

object OOBasics extends App {

  val person = new Person("John", 26)
  person.greet("Gun")
}
// constructor
class Person(name: String, val age: Int = 0) {
  //body - not really expression
  val x = 2

//  println(x + 1)
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

  //Overloading
  def greet(): Unit = println(s"$name says Hello")

  //multiple constructor
  def this(name: String) = this(name,0)
  def this() = this("Gun")

}

//class parameters != FIELDS
//need to add val/var to class parameters to covert it to field