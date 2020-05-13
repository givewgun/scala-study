package lectures.part2oop

object AbstractDataTypes extends App {

  //abstract
  abstract class Animal{
    val creatureType: String = "wild"
    def eat: Unit
  }

  class Dog extends Animal{
    override val creatureType: String = "Canine"
     def eat: Unit = println("Crunch")
  }

  //traits
  trait Carnivore{
    def eat(animal: Animal): Unit
    val preferedMeal = "meat"
  }

  trait ColdBlood
  class Crocodile extends Animal with Carnivore with ColdBlood {
    override val creatureType: String = "croc"

    override def eat: Unit = println("Nom")

    override def eat(animal: Animal): Unit = println(s"Im a croc and I'm eating ${animal.creatureType}")

    val dog = new Dog
    val croc = new Crocodile
    croc.eat(dog)

  }

  //traits vs abstract classes
  //1 - traits do not have constructor parameters
  //2 - multiple traits may be inherited by the same class
  //3 - traits = behavior, abstract class = type of thing

}
