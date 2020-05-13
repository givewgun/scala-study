package lectures.part2oop

object Exceptions extends App {

  val x: String = null
//  println(x.length) // this will crashed with NullPointerException

  //throwing and catching exceptions

//  val aWeirdValue: String = throw new NullPointerException // expression

  // throwable classes extends Throwable class
  // Exception and Error are the major Throwable subtypes

  //2. how to catch exceptions
  def getInt(withExceptions: Boolean): Int = {
    if (withExceptions) throw new RuntimeException("No Int for You")
    else 43
  }

  val potentialFail = try{
    //code that might fail
    getInt(true)
  }catch {
    case e: RuntimeException => println("caught a Runtime exception")
  }finally {
    //will run no matter what
    //optional
    //does not influence the return type of this expression
    // use finally for side effects
    println("finally")
  }

  //3 how to define your own exceptions
  class MyException extends Exception
  val exception = new MyException



}
