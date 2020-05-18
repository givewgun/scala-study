package excercises.part2

object PartialFunctions extends App {

  /*
    1 - construct a PF instance yourself
    2 - dumb chatbot as PF
   */
  val aManualFussyFunction = new PartialFunction[Int, Int] {
    override def apply(v1: Int): Int = v1 match {
      case 1 => 42
      case 2 => 444
      case 5 => 66666
    }

    override def isDefinedAt(x: Int): Boolean =
      x ==1 | x == 2 | x == 5
  }

  val i = 4
//  println({
//    if (aManualFussyFunction.isDefinedAt(i)) aManualFussyFunction(1)
//    else None
//  })


  //chat bot
  val chatbot: PartialFunction[String, String] = {
    case "hello" => "HIIIIII"
    case "bye" => "BYEEEEEEEE"
  }

  //scala.io.Source.stdin.getLines().foreach(line => println("chatbot says: " + chatbot(line)))
  scala.io.Source.stdin.getLines().map(chatbot).foreach(println) //scala.io.Source.stdin.getLines() is like a stream list of lines

}
