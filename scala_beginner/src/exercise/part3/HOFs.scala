package exercise.part3

object HOFs extends App{

  /*
    2. toCurry(f: (Int, Int) => Int) => (Int => Int => Int)
    fromCurry(f: (Int => Int => Int)) => (Int, Int) => Int)

    3.compose(f,g) => x => f(g(x))
      andThen(f,g) => x => g(f(x))
   */

  //last => is the original func --
  // we try to create another fucntion 9before last =>) to map it to the original func
  def toCurry(f: (Int, Int) => Int): (Int => Int => Int) =
    x => y => f(x,y)
  def fromCurry(f: Int => Int => Int): (Int, Int) => Int =
    (x, y) => f(x)(y)

  def compose[A,B,T](f: A => B, g: T => A): T => B =
    x => f(g(x))

  def andThen[A,B,C](f: A => B, g: B => C): A => C =
    x => g(f(x))

  def superAdder2: (Int => Int => Int) = toCurry(_ + _) //superAdder2(4)(17) 4 + 17, _ + _ is f:(Int, Int) => Int
  def add4 = superAdder2(4)
  println(add4(17))

  val add2 = (x: Int) => x + 2
  val mul3 = (x: Int) => x * 3
  val composed = compose(add2, mul3)
  val ordered = andThen(add2, mul3)

  println(composed(4))
  println(ordered(4))


}
