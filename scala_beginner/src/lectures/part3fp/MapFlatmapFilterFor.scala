package lectures.part3fp

object MapFlatmapFilterFor extends App {

  val list = List(1,2,3)
  println(list.head)
  println(list.tail)

  //map
  println(list.map(_ + 1))
  println(list.map(_ + "SSSS"))

  //filter
  println(list.filter( _ > 2))

  //flatmap
  val toPair = (x: Int) => List(x, x+1)
  println(list.flatMap(toPair))

  val numbers = List(1,2,3,4)
  val chars = List('a','b','c','d')
  val colors = List("black", "white")

  //print all posible combi of 2 list
  //basically 3 nested loop
  //" iteration
  val combinations = numbers.filter(_ % 2 == 0).flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + "-" + color)))
  println(combinations)

  //foreach
  list.foreach(println)

  //for-combinations
  //PREFERED
  val forCombination = for {
    n <- numbers if n % 2 == 0 //guard
    c <- chars
    color <- colors
  } yield "" + c + n + "-" + color
  println(forCombination)

  for{
    n <- numbers //for n in numbers!!
  } println(n)
  // numbers.map(n => println(n))

  //syntax overload
  list.map { x =>
    x * 2
  }


}
