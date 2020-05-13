package lectures.part2oop

object Objects extends App {

  //SCALA DOESN"T HAVE CLASS_LEVEL FUNC ('static')
  object Person{ // type + its instance
    //"static"/"class" - level functionality
    val N_EYES = 2
    def canFly: Boolean = false

    //factory method
    def apply(mother: Person, father: Person): Person = new Person("Gun")
  }
  class Person(val name: String){
    //instance-level functionality
  }
  //COMPANIONS DESIGN PATTERN
  //object + class same name in same scope

  println(Person.N_EYES)
  println(Person.canFly)


  val mary = new Person("Mary")
  val john = new Person("John")
  println(mary == john) // false as refrered todifferent instance

  //Scala object (plain) = singleton instance
  val person1 = Person
  val person2 = Person
  println(person1 == person2) // true cuz same instance (pointer points to same instance)

  val bobbie = Person(mary, john)

  //Scala applications = scala object with
  //def main(args: Array[String]): Unit = {....}
  //equals to extends App






}
