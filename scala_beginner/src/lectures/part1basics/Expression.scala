package lectures.part1basics

object Expression extends App {
  val aCondition = true
  val aConditionValue = if(aCondition) 5 else 3 // IF ""EXPRESSION"" GIVES BACK A VALUE !!!!!!!!!!!!!!!!!!!!!
  val exp = if(aCondition) 5 else 3
  println(exp + 8)

  //SCALA DISCOURAGE LOOP!!!!!!!!
  //NEVER WRITE THIS LOOOOOOOOOOOOOOOOOOP
  var i = 0
  while(i < 10){
    println(i)
    i += 1
  }

  //EVERYTHING IN SCALA IS AN EXPRESSION!

  var aVariable = 1

  val aWeirdValue = (aVariable = 3) // Unit == void

  // side effects: println(), while, reassigning

  // Code blocks = expression -> val block = val last expression

  val aCodeBlock = {
    val y = 2
    val z = y+1

    if (z > 2) "hello" else "goodbye"
  }


}
