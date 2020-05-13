package lectures.part2oop

object AnonymousClasses extends App {

  //anonymous class -> new ClassName {....} -> compiler create a new anonymous class (not the same name that was implemented prior)
  //on the spot implementation / overriding
  //must provide same paremters as original class constructor
  //can be used for abstract or simple class
  //implement all abstract method or field

  abstract class Animal{
    def eat: Unit
  }
  val funnyAnimal: Animal = new Animal{
    override def eat: Unit = println("DSDSDSDSDSD")
  }

  class Person(val name: String){
    def says = println(s"sdfsdfd $name")
  }
  val jim = new Person("Gun"){
    override def says: Unit = println(s"FUCKKKKKKKKKK ${name}") // problem -> anonymous can't use param? -> must be val in original to use
  }




}
