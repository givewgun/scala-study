package lectures.part2afp

object CurriesAndPAF extends App {

  //curried function -> f that return other f as result
  val superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3) // Int => Int = y => 3 + y
  println(add3(5))
  println(superAdder(3)(5)) //curried function

  //curried METHOD
  def curriedAdder(x: Int)(y: Int): Int = x + y

  val add4: Int => Int = curriedAdder(4) //must state type
  // cuz if not the compiler is confused cuz parameter is not complete
  //lifting = ETA-EXPANSION
  //cuz we can't use method in HOFs

  //functions != method (JVM limitatin)
  //ETA
  def inc(x: Int) = x + 1
  List(1,2,3).map(inc) // Compiler convert using ETA to x => inc(x)

  //Partial functions applications
  val add5 = curriedAdder(5) _ // _ convert this to Int => Int

  //Exercise
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simepleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y
  //come up with diffrent add7 func
  val add7 = (x: Int) => simpleAddFunction(7, x)
  val add7_2 = simpleAddFunction.curried(7) //turn to curried func
  val add7_6 = simpleAddFunction(7, _: Int)

  val add7_3 = curriedAddMethod(7) _ //PAF
  val add7_4 = curriedAddMethod(7)(_) //PAF = alternative syntax

  val add7_5 = simepleAddMethod(7, _: Int) //alternative syntax for turing methods into a function
                //y => simpleAddMethod(7, y)

  // _ are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c
  def insertName = concatenator("Hello I'm ", _: String, "how are you") // x: String => concatenator(hello, x, howareu)
  println(insertName("Gun"))

  val fillInBlank = concatenator("Hello", _: String, _: String) // (x, y) => concatenator("hello", x, y)
  println(fillInBlank("Gun", "Nug"))

}
