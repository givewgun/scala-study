package lectures.part2oop

object CaseClasses extends App {
  /*
  equals, hashCode, toString must be override in many user class
   */

  case class Person(name: String, age: Int)

  //case class
  //1.class parameters are fields
  val jim = new Person("Jim", 11)
  println(jim.age)

  //2. sensible toString
  // println(instance -> println(instance.toString)) // syntactic sugar
  println(jim)

  //3. equals and hashCode implemented Out of The Box
  val jim2 = new Person("Jim", 11)
  println(jim == jim2)

  //4. CCs have handy copy method
  val jim3 = jim.copy(age = 45)

  //5. CCs have companion object -> use companion objects apply as constructor
  val thePerson = Person
  val mary = Person("Mary", 23)

  //6. CCs are serializable!!!!!! AKKA

  //7. CCs have extractor patterns = CCs can be used in PATTERN MATCHING

  case object UnitedKingDom{
    def name: String = "sdfbshdfhadf"
  }



}
