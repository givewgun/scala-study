package lectures.part2afp

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1 //Function1[Int, Int] === Int => Int


  val aFussyFuction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else throw new FunctionNotApplicableException


  class FunctionNotApplicableException extends RuntimeException

  //proper function
  val aNicerFussyFuction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }
   // {1,2,5} => Int
  //called partial func as accept only partial part of Int domain

  //partial function
  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  } //partial function value (like case but sweeter)

  println(aPartialFunction(2))
//  println(aPartialFunction(2222)) //MatchError

  //PF utilities
  println(aPartialFunction.isDefinedAt(67)) //check if have case in PF //false

  //lift -> safe
  val lifted = aPartialFunction.lift // Int => Option[Int]
  println(lifted(1)) //Some(42)
  println(lifted(3333)) //None

  //cahined PF
  val pfChained = aPartialFunction.orElse[Int, Int] {
    case 3333 => 67
  }
  println(pfChained(3333)) //67

  // PF extends normal function
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  //HOFs accepts partial function as well
  val aMappedList = List(1,2,3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  }

  /*
    PF: can only have ONE parameter type
   */









}
