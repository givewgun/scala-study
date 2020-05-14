package lectures.part3fp

object WhatsAFunction extends App {
  //Dream: use func as first class elements
  //problem: oop -> use method inside a class like static etc
  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  //cann instance like a function (duc-like class)
  print(doubler(2))

  // function types = Function1[A,B]
  val stringToIntConverter = new Function[String, Int] {
    override def apply(string: String): Int = string.toInt
  }
  println(stringToIntConverter("3") + 4)

  //val adder: ((Int, Int) => Int) = (a: Int, b: Int) => a + b
  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }


  //Functions type Function2[A,B,R] === (A,B) => R
  // ALL SCALA FUNCTIONS ARE OBJECT (INSTANCE)






}


//java-like
trait Action[A,B]{
  def execute(element: A): B = ???
}

//scala
trait MyFunction[A,B]{
  def apply(element: A): B = ???
}