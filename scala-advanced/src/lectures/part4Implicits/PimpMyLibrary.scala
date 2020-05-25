package lectures.part4Implicits

object PimpMyLibrary extends App {

  //Enrichment
  //2.isPrime


  //Type enrichment (wrapper for class)
  //implicit class only has only 1 param!
  implicit class RichInt(val value: Int) extends AnyVal {
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = math.sqrt(value)

    //exercise
    def times(function: () => Unit): Unit = {
      def timesAux(n : Int): Unit = {
        if (n <= 0) ()
        else {
          function()
          timesAux(n - 1)
        }
      }

      timesAux(value)
    }

    def *[T](list: List[T]): List[T] = {
      def concatnate(n: Int): List[T] = {
        if (n <= 0) List()
        else concatnate(n - 1) ++ list
      }

      concatnate(value)
    }
  }

  implicit class RicherInt(richInt: RichInt) {
    def isOdd: Boolean = richInt.value % 2 != 0
  }
  //42.isOdd //compiler doesn't wrap implicit class again!

  new RichInt(42).sqrt
  42.isEven

  //type enrichment = pimp

  1 to 10

  import scala.concurrent.duration._
  3.seconds //3.seconds

  //compiler doesn't do multiple searches

  /*
    Enrich the String Class
      - asInt
      - encrypt
        "John" -> Lnjp
    Enrich int class
      - times(function)
      3.times(() => ....)
      - *
      3 * List(1,2) = List(1,2,1,2,1,2)
   */

  implicit class RichString(string: String) {
    def asInt: Int = Integer.valueOf(string) //java.lang.Integer -> Int
    def encrypt(cypherDistance: Int): String = string.map(c => (c + cypherDistance).asInstanceOf[Char])
  }
  println("3".asInt)
  println("Gun".encrypt(2))

  3.times(() => println("scala rocks"))
  println(4 * List(1,2))

  // "3" / 4
  implicit def stringToInt(string: String): Int = Integer.valueOf(string)
  println("6" / 2) //compliter looks for any implicit class / method / val that convert String to Int

  //equivalent: implicit class RichAlternativeInt(value : Int)
  class RichAlternativeInt(value : Int)
  implicit def enrich(value: Int): RichAlternativeInt = new RichAlternativeInt(value)

  //danger zone
  //If there is a bug in implicit def -> very fucking hard to debug!!!!!!
  implicit def intToBoolean(i: Int): Boolean = i == 1
  /*
  if (n) do something
  else do something else
   */

  val aConditionValue = if (3) "OK" else "Something Wrong" // "Something Wrong" (bugggg as implicit only checks i == 1)
}
