package lectures.part2oop

object MethodNotations extends App {

  class Person(val name: String, favoriteMovie: String){
    def likes(movie: String): Boolean = movie == favoriteMovie
    def +(person: Person): String = s"${this.name} is hangging out with ${person.name}"
    def unary_! : String = s"$name, what the heck!"
    def isAlive: Boolean = true
    def apply(): String = s"$name is a god"
  }

  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  //infix notation = operator notation (only 1 parameter method)
  //syntactic sugar
  println(mary likes "Inception")

  //"operators" in Scala
  //operator like in math = freedom in naming
  val tom = new Person("Tome", "Fight Club")
  println(mary + tom)
  println(mary.+(tom))

  println(1 + 2)
  println(1.+(2))

  //ALL OPERATORS ARE METHOD
  //Akka actors have ! ?

  //prefix notation
  val x = -1 // equivalent with 1.unary_-
  val y = 1.unary_-
  //unary_ prefix only works with - + ~ !
  println(!mary)
  println(mary.unary_!)

  //postfix notation
  //function that doesn't receive any parameters
  //rarely used
  println(mary.isAlive)
//  println(mary isAlive)

  //apply
  println(mary.apply())
  println(mary()) //call the apply() method in class

}
