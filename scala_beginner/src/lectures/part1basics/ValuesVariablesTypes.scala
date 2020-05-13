package lectures.part1basics

object ValuesVariablesTypes extends App{
  val x: Int =  42
  println(x)

  // vals are immutable
  // types of val :type is optional (inferred by compiler)

  val s: String = "YO"
  val b: Boolean = true
  val c: Char = 'a'
  val sh: Short = 1222
  val l: Long = 1111111111111111L
  val f: Float = 1.0f
  val d: Double = 1.0

  //Variable
  var aVariable: Int = 4
  aVariable = 1 //side effect
  aVariable += 1

}
