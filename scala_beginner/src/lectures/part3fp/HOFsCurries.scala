package lectures.part3fp

object HOFsCurries extends App {

  //how to read???
  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = null
  //higher-order function -> map flat-map filter

  // fucntion that applies a function n-times over a value x
  // nTimes(f, n, x)
  // nTimes(f, 3, x) = f(f(f(x))) = nTimes(f, 2, f(x))
  // nTimes(f, n, x) = f(f(f...(x))) = nTimes(f, n-1, f(x))
  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if(n <= 0) x
    else nTimes(f, n-1, f(x)) //f.apply(x)
  }

  val plusOne = (x: Int) => x + 1 // an anonymous fucntion !!
  println(nTimes(plusOne, 10, 1))

  //API style

  // have service that will take only what val you want to put in -> do higher-func core for you
  // ntb(f,n) = x => f(f(f(...f(x))))
  //increment10 = ntb(plusOne, 10) = x => plusOne(plusOne(...(x)))
  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) ={
    // new labda to receive another parameter (real one)
    if(n <= 0) (x: Int) => x // 0 times
    else (x: Int) => nTimesBetter(f, n-1)(f(x)) // remember, f is a function!!!
  }

  val plus10 = nTimesBetter(plusOne, 10)
  println(plus10(1))

  //curried function
  //helpful for define helper func to use later on various value
  val superAdder: (Int => (Int => Int)) = (x: Int) => (y: Int) => x + y
  val add3 = superAdder(3) // y => 3 + y
  println(add3(10))
  println(superAdder(3)(10))

  //function with multiple parameter list
  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  //to use curried function -> define type of smaller func that use it!
  val standardFormat: (Double => String) = curriedFormatter("%4.2f") //use later for x (apply)
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

  println(standardFormat(Math.PI))
  println(preciseFormat(Math.PI))


}
