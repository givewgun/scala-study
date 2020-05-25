package playground

object ScalaPlayground extends App {

  implicit class RichInt(value: Int) {
    def apply(anotherInt: Int): Int = value * anotherInt
  }
  println(8 / 2(2+2))
}
