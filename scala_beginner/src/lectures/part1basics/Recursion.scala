package lectures.part1basics

import scala.annotation.tailrec

object Recursion extends App {

  //recursion use stack frame
  //jvm put this stackframe in limited jvm memory - recursion depth is too big -> StackOverflowError
  //unless we write in smart ways
  def factorial(n: Int): Int = {
    if (n <= 1) 1
    else{
      println("Computing factorial of " + n + "I first need factorial of " + (n-1))
      val result = n * factorial(n-1)
      println("Computed factorial of " + n)
      result
    }
  }

  //smart ways
  def anotherFactorial(n: Int): BigInt = {
    @tailrec
    def factHelper(x: Int, accumulator:BigInt): BigInt ={
      if (x <= 1) accumulator
      else factHelper(x - 1, x * accumulator)
    }

    factHelper(n,1)
  }

  println(anotherFactorial(6000))

  /*
  anotherFactorial(10) = factHelper(10,1)
  = factHelper(9, 10 * 1)
  = factHelper(8, 9 * 10 * 1)
  = factHelper(7, 8 * 9 * 10 * 1)
    ...
  = factHelper(2, 3 * 4 * 5 * 6 * 7 * 8 * 9 * 10 * 1)
  = factHelper(1, 1 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 10)
  = return  1 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 10
   */

  /*why factHelper works?????
 cuz don't need to return immediate call (not assign)
 reserve stackframe
 Tail Recursion = use recursive call as the LAST expression
  - @tailrec -> tell compiler to warn you
  - use accumulators!!! num = num recursive call


  */

  // WHEN YOU NEED LOOPS< USE TAIL_RECURSION

}
