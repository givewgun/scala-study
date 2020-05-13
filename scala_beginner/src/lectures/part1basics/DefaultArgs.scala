package lectures.part1basics

object DefaultArgs extends App{

  def trFact(n: Int, acc: Int = 1): Int = {
    if (n <= 1) acc
    else trFact(n-1, n*acc)
  }

  val fact10 = trFact(10)

  def savePicture(format: String = "jpg" ,width: Int = 800, height: Int): Unit = {
    println("save file")
  }

  savePicture(width = 12200, height = 600, format = "Sdasd")

  /*
  pass in every leading arg
  name the arg
   */
}
