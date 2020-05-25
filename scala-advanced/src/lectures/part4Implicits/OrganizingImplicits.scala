package lectures.part4Implicits

object OrganizingImplicits extends App {

  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  //implicit val normalOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _)

  //not compile cuz two implicits so compiler cannot choose
  println(List(1,4,5,3,2).sorted) //reverseOrdering take preference than implicit in scala.Predef

  // scala.Predef

  /*
    Implicits (used as implicits parameters)
      - val/var
      - object
      - accessor methods = defs with no parenthesis !
   */

  //Exercise
  case class Person(name: String, age: Int)

  val persons = List(
    Person("Steve", 30),
    Person("John", 22),
    Person("Gun", 22)
  )

  implicit val alphabetOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  println(persons.sorted)

  /*
    Implicit scope priority
      - normal scope = LOCAL SCOPE
      - imported scope
      - companions of all types involved in the method signature
        - List
        - Ordering
        - all the types involved = A or any super types
   */
  // def sorted[B >: A](implicit ord: Ordering[B]): List[B]


}
