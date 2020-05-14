package exercise.part3

object WhatsAFunction extends App {
  //def concatenator: ((String, String) => String) = (a: String, b: String) => a + b
  def concatenator: ((String, String) => String) = new Function2[String, String, String] {
    override def apply(a: String, b: String): String = a + b
  }
  println(concatenator("hello", "scala"))

  // Function1[Int, Function1[Int,Int]]
  // val superAdder: (Int) => Function1[Int, Int] = (x: Int) => (y: Int) => x + y
  val superAdder: Function1[Int, Function1[Int,Int]] = new Function1[Int, Function1[Int,Int]]{
    override def apply(x: Int): Function1[Int,Int] = new Function1[Int,Int]{
      override def apply(y: Int): Int = x + y
    }
  }

  //anonymous func
  val superAdd = (x: Int) => (y:Int) => x + y //x => func that has apply(y) of x + y

  val adder3 = superAdder(3) //x = 3
  println(adder3(4)) //x = 3, y = 4
  println(superAdder(3)(4)) //curried function
  println()




}
