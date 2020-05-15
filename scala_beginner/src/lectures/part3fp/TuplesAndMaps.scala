package lectures.part3fp

object TuplesAndMaps extends App {

  //tuples = finite ordered "list"
  //val aTuple = new Tuple2(2, "Gun") // Tuple2[Int, String] = (...)
  //or
  //22 tuples type like func
  val aTuple = (2, "Gun")
  println(aTuple._1)
  println(aTuple.copy(_2 = "DSSD"))
  println(aTuple.swap) // ("Gun", 2)

  // Maps - keys -> value
  //careful when mapping keys!!!!!!! -DUP = data loss
  val aMap: Map[String, Int] = Map()

  val phoneBook = Map(("Jim", 5555), "Gun" -> 7878).withDefaultValue(-1)
  // a-> b is syntactic sugar for (a,b)
  println(phoneBook)

  println(phoneBook.contains("Gun"))
  println(phoneBook("Jim"))
  println(phoneBook("SDSDS")) // -1 as defaultValue

  //add more key val
  val newParing = "Mary" -> 658
  val newPhoneBook = phoneBook + newParing

  //functional on map
  //map flatMap filter
  println(phoneBook.map(pair => pair._1.toLowerCase -> pair._2))

  //filter  keys map value
  println(phoneBook.view.filterKeys(k => k.startsWith("J")))
  //map value
  println(phoneBook.view.mapValues(v => "02-" + v))

  //conversion to other collections
  println(phoneBook.toList) // List(tup1, tup2)
  println(List(("Gun" , 555), ("sdgsg", 888)).toMap)
  val names = List("A", "B", "C", "AAAAA", "Bob")
  println(names.groupBy(name => name.charAt(0))) //group byyyyyyyy

}
