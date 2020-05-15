package exercise.part3

object MapFlatmapFilterFor extends App{

  val numbers = List(1,2,3,4)
  val chars = List('a','b','c','d')
  val colors = List("black", "white")

  //print all posible combi of 2 list
  //basically 3 nested loop
  //" iteration
  val combinations = numbers.flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + colors)))
  println(combinations)

  /*
    1.myList support for comprehensions?
      map(f: A => B): MyList[B]
      filter(p: A => Boolean): MyList[A]
      flatMap(f: A => MyList[B]): MyList[B]
    2. A small collection of at most ONE element - Map[+T]
   */

}
