package exercise

import scala.annotation.tailrec
import scala.jdk.Accumulator

object Recursion extends App {

  @tailrec
  def stringConcat(s: String, n: Int, accumulator: String): String = {
    if (n  ==  0) accumulator
    else stringConcat(s, n - 1, accumulator + s)
  }
  val s = "Gun"
  println(stringConcat(s, 0, s))

//  @tailrec
//  def fibbonacci(n: Int, last: Int, nextToLast: Int): Int ={
//    if (n == 1 || n == 0) last + nextToLast
//    else fibbonacci(n-1, last + nextToLast, last)
//  }
//  println(fibbonacci(8, 1, 0))

  def fibonacci(n: Int): Int = {
    @tailrec
    def fibboTailRecur(i: Int, last: Int, nextToLast: Int): Int = {
      if(i >= n) last
      else fibboTailRecur(i+1, last + nextToLast, last)
    }

    if (n <= 2) 1
    else fibboTailRecur(2, 1, 1)
  }
  println(fibonacci(8))

  def isPrime(n: Int): Boolean ={
    @tailrec
    def isPrimeUntil(t: Int, isStillPrime: Boolean): Boolean = {
      if (!isStillPrime) false
      else if (t <= 1) isStillPrime
      else{
        isPrimeUntil(t-1, isStillPrime &&  (n % t != 0 ))
      }
    }

    isPrimeUntil(n / 2, true)
  }
  println(isPrime(2003))
  println(isPrime(629))
}
