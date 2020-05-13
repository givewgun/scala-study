package lectures.part1basics

object StringOps extends App {

  val str: String = "Hello, I am learning Scala"

  println(str.charAt(2))
  println(str.substring(7,11))
  println(str.split(" ").toList)
  println(str.startsWith("Hello"))
  println(str.replace(" ", "-"))
  println(str.toLowerCase().toUpperCase())
  println(str.length)

  //scala util
  val aNumberString = "45"
  val aNumber = aNumberString.toInt
  println('a' +: aNumberString :+ 'z')
  println(str.reverse)
  println(str.take(2))

  //Scala-specific: String interpolators

  //S-interpolator - injecting into string (with s leading!!)
  val Name = "Gun"
  val age = 11
  val greeting = s"SJSDKOSD $Name dfsadf ${age+222} yesss"

  //F-interpolator
  val speed = 1.2f
  val myth = f"${Name} can eat $speed%2.2f burgers in 1 min"

  // raw-interpolator
  println(raw"GUnnnnn \n asfasdasd")
  val escaped = "This is a \n newline"
  println(raw"$escaped")

}
