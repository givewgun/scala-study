package lectures.part2oop

object Inheritance extends App {

  //Single class inheritance
  sealed class Animal{
    val creatureType = "wild"
    def eat = println("nom")
  }

  class Cat extends Animal{
    def crunch = {
      eat // = super.eat
      println("crunch")
    }
  }

  val cat = new Cat
  cat.crunch

  //constructors
  class Person(name: String, age: Int){
    def this(name: String) = this(name, 0)
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name)

  //overriding
  class Dog(override val creatureType: String) extends  Animal{
//    override val creatureType: String = "domestic"
    override def eat: Unit = {
      super.eat
      println("DOG EAT")
    }
  }
  val dog = new Dog("K9")

  //type substitution - polymorphism
  val unknownAnimal: Animal = new Dog("What")

  //overriding vs overloading

  //super

  //preventing override
  //1- use final on member
  //2. final on class -> can't extends
  //3. seal the class = extends classes in this file , prevent extension in other file

}
