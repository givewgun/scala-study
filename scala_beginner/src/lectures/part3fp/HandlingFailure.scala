package lectures.part3fp

import scala.util.{Failure, Success, Try}


object HandlingFailure extends App {

  //create success and failure
  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("SUPER FAIL"))

  println(aSuccess)
  println(aFailure) //Failure(java.lang.RuntimeException: ....)

  def unsafeMethod(): String = throw new RuntimeException("NO STRINGGGGGG")

  //Try objects via apply method
  //val potentialFailure = Try(unsafeMethod())
  //equals to
  val potentialFailure = Try {
    //code that might throw
  }

  //utilities
  println(potentialFailure.isSuccess) //false
9
  //orElse
  def backupMethod(): String = "Backup"
  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))

  //If designed APis
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
  def betterBackupMethod(): Try[String] = Success("Backup")
  val betterFallback = betterUnsafeMethod() orElse betterBackupMethod()

  //map, flatmap, filter
  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x*10)))
  println(aSuccess.filter(_ > 10)) //turn to a failure with exception
  // => for comprehentsion


  println(aSuccess.map(x => println(x)))


}
