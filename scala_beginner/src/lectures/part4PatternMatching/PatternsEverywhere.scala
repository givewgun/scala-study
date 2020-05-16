package lectures.part4PatternMatching

object PatternsEverywhere extends App {

  //big idea
  try{
    //code
  }catch {
    case e: RuntimeException => "runtime"
    case npe: NullPointerException => "npe"
    case _ => "something else"
  }

  //Catch are actually match
  /*
    try{
      //code
    } catch(e) [
      e match {
        case e: RuntimeException => "runtime"
        case npe: NullPointerException => "npe"
        case _ => "something else"
      }
    }
   */

  //big idea 2
  val list = List(1,2,3,4)
  val evenOnes = for {
    x <- list if x % 2 == 0 //?!!!
  } yield 10 * 2

  //generators are also based on pattern matching!!!
  val tuples = List((1,2), (3,4))
  val filterTuples = for { //tuple cannot do for yield?
    (first , second) <- tuples  if second > 2 //tuple(t
  } yield (first + second)

  filterTuples.map(x => {
    println(x + 111)
  })

  //case classes, :: operators, ....

  //big idea #3
  val tuple = (1,2,3)
  val (a,b,c) = tuple //or a,b,c
  println(b)
  // multiple value definitions based on pattern matching
  val head :: tail = list
  // 1, List(2,3,4)

  //big Idea #4
  //partial function
  val mappedList = list.map {
    case v if v % 2 == 0 => v + " is even"
    case 1 => "the one"
    case - => "someting else"
  }// partial function literal

  val mappedListEquivalent = list.map( x => x match {
    case v if v % 2 == 0 => v + " is even"
    case 1 => "the one"
    case - => "someting else"
  })

}
