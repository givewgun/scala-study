package lectures.part4Implicits
import java.{util => ju}
object ScalaJavaConversion extends App {

  import collection.JavaConverters._

  val javaSet: ju.Set[Int] = new ju.HashSet[Int]()
  (1 to 5).foreach(javaSet.add)
  println(javaSet) // [1 2 3 4 5]

  val scalaSet = javaSet.asScala

  /*
    Iterator
    Iterable
    ju.List -> scala.mutable.Buffer
    ju.Set -> scala.mutable.Set
    ju.Map -> scala.mutable.Map
   */

  import scala.collection.mutable._
  val numberBuffer = ArrayBuffer[Int](1,2,3)
  val juNumberBuffer = numberBuffer.asJava

  println(juNumberBuffer.asScala eq numberBuffer) // true (ref are equal)

  val numbers = List(1,2,3)
  val juNumbers = numbers.asJava
  val backToScala = juNumbers.asScala
  println(backToScala eq numbers) // false (different instance mutable / immutable)
  println(backToScala eq numbers) //content is the same

//  juNumbers.add(7) //Exception as juNumbers is suppose to be immutable (from scala immutable List)

  /*
    Exercise
    create a Scala-Java Optional-Option
   */
  class ToScala[T](value: => T) {
    def asScala: T = value
  }

  implicit def asScalaOptional[T](o: ju.Optional[T]): ToScala[Option[T]] = new ToScala[Option[T]](
    if (o.isPresent) Some(o.get)
    else None
  )

  val juOptional: ju.Optional[Int] = ju.Optional.of(2)
  val scalaOption = juOptional.asScala
}
