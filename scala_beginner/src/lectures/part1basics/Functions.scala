package lectures.part1basics

object Functions extends App {

  def aFunction(a: String, b: Int): String = {
    a + "  " + b
  }

  println(aFunction("HELLO", 3))

  def paramLess(): Int = 42
  println(paramLess())
  print(paramLess) //paramless can be called by only name

  def aRepeatedFunc(a: String, b: Int): String = {
    if (b == 1) a
    else a + aRepeatedFunc(a, b-1)
  }

  println(aRepeatedFunc("HELLO", 3))

  def aFunctionWithSideEffects(aString: String): Unit = println(aString * 10)
  aFunctionWithSideEffects("SDSDSDD")

  // WANt TO lOOP -> RECURSION!!!!!!!!!!!!!!!!!!


  def aBigFunc(n: Int): Int = {
    def aSmallFunc(a: Int, b: Int): Int = a + b

    aSmallFunc(1,n)
  }

}
