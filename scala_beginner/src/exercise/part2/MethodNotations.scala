package exercise.part2
import scala.language.postfixOps

object MethodNotations extends App {
  class Person(val name: String, val age: Int = 0){
    def +(name: String): Person = new Person(s"${this.name} $name")
    def unary_+ : Person = new Person(name, age + 1)
    def learns(topic: String) = println(s"$name learns $topic")
    def learnsScala = learns("Scala")
    def apply(times: Int = 0): String = s"$name watched Inception $times times"
  }

  val mary = new Person("Mary")
  println((mary + "the rockstar")(54))
  println((+mary).age)
  println(mary learnsScala)

}
