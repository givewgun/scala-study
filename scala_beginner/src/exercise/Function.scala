package exercise

object Function extends App {
  def factorial(n :Int): Int ={
    if (n <= 0) 1
    else n * factorial(n-1)
  }

  def fibbonacci(n: Int): Int ={
    if (n == 1 || n == 0) 1
    else fibbonacci(n-1) + fibbonacci(n-2)
  }

  def isPrime(n: Int): Boolean ={
    def isPrimeUntil(t: Int): Boolean = {
      if (t <= 1) true
      else{
        n % t != 0 && isPrimeUntil(t-1)
      }
    }
    isPrimeUntil(n / 2)
  }

  println(fibbonacci(12))
}
