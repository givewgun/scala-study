package lectures.part3fp

object Options extends App{

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  //unsafe APIs
  def unsafeMethod(): String = null
  //val result = Some(unsafeMethod()) //Wrong as Some will always have a valid value inside
  val result = Option(unsafeMethod()) //Some or None
  //Options -> do the null check for us
  //if null -> it will return None
  //  then we can use orElse to do others method

  //chained method
  def backupMethod(): String = "A valid result"
  val chainedReseult = Option(unsafeMethod()).orElse(Option(backupMethod()))

  //Redesign better unsafe APIs
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("WORK")

  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()
  // functions on option
  println(myFirstOption.isEmpty) //false
  println(myFirstOption.get) //UNSAFE cuz if val is null -> NullPointerException , if nor we get Some(...)
  //but not .get() of map cuz that return option

  //map flatmap filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(x => x > 10))
  println(myFirstOption.flatMap(x => Option(x * 10))) //Some(40)
}
