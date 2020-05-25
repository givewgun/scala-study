package excercises.part4

import lectures.part4Implicits.TypeClasses.{User, john}
import scala.language.postfixOps

object EqualityPlayground extends App {
  /*
    Equal type class
   */
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  object FullEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name && a.email == b.email
  }

  /*
    Exercise: implemetn the TC pattern to Equality
   */
  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean =
      equalizer.apply(a, b)
  }

  val john = User("John", 22, "kuy@gmail.com")
  val anotherJohn = User("John", 44, "sus@gmail.com")
  println(Equal(john, anotherJohn)) // AD-HOC polymorphism

  //AD-HOC poly morphism


  /*
    exercise - improve the Equal RC with and implicit conversions class
    ===(anotherValue: T)
    !==(anotherValue: T)
   */
  implicit class TypeSafeEqual[T](value: T) {
    def ===(other: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(value, other)
    def !==(other: T)(implicit equalizer: Equal[T]): Boolean = !equalizer.apply(value, other)
  }

  println(john === anotherJohn)
  /*
    john.===(anotherJohn)
      - is there .===() in User ? No
      - try wrapping User with something that has .===()
      - wrap User to
    new TypeSafeEqual[User](john).===(anotherJohn) //find equalizer
      -find implicit Equal[User] object -> NameEquality
    new TypeSafeEqual[User](john).===(anotherJohn)(NameEquality)
   */
  /*
    Type SAFE
   */
//  println(john === 43) //must be the same type!!


}
