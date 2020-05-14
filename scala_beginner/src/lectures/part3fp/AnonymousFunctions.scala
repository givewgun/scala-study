package lectures.part3fp

object AnonymousFunctions extends App {

  //Anonymous func == LAMBDA

  //dont have to assing to val -> ... => ... is a function -> can be pass!

  // => apply
  val doubler: Int => Int = (x) => x * 2 //state the type of ret -> compiler will check for u

  //multiple params
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  //no params
  val justDoSomething: () => Int = () => 3
  println(justDoSomething) // function self -> garbage
  println(justDoSomething()) // function call -> 3

  //curly braces with lambda
  val stringToInt ={ (str: String) =>
    str.toInt
  }

  //MOAR Syntactic sugar
  val niceIncrementer: Int => Int = _ + 1 // equivalent to x => x+1
  val niceAdder: (Int, Int) => Int = _ + _ // equivalent to (a,b) => a + b
}
