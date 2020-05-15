package lectures.part3fp

import scala.util.Random

object Sequences extends App {
  // have well defined order
  // can be index

  val aSequence = Seq(1,2,3,4)
  println(aSequence) // List(1,2,3,4) cuz it apply to a subclass of List
  print(aSequence.reverse)
  println(aSequence(2)) //idx 2
  println(aSequence ++ Seq(5,6))
  println(aSequence.sorted) // ascending
  println(aSequence.sorted(Ordering.Int.reverse)) //Descending

  //Ranges
  val aRange: Seq[Int] = 1 to 10
  aRange.foreach(println) // === aRange.foreach(n => println(n))
  for(n <- 1 to 10){
    println(n)
  }

  //Lists
  val aList = List(1,2,3)
  val prepended = 42 +: aList :+ 89 // 42,1,2,3, 89  : onside of list

  val apples5 = List.fill(5)("apple")
  println(aList.mkString("--")) //1--2--3

  //Array
  val numbers = Array(1,2,3,4)
  val threeElem = Array.ofDim[Int](3)
  threeElem.foreach(println) // 0 0 0 //default val of [Type]

  //mutation
  numbers(2) = 0 //syntax sugar for numbers.update(2,0)

  //array and seqs
  val numbersSeq: Seq[Int] = numbers // implicit conversion!!! automatically convertArray to WrappedArray

  //vector
  val vector: Vector[Int] = Vector(1,2,3)

  //vector vs list
  val maxRun = 1000
  val maxCapacity = 1000000
  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random
    val times = for{ //[System.nanoTime() - currentTime, System.nanoTime() - currentTime...]
        it <- 1 to maxRun
    } yield {
        val currentTime = System.nanoTime()
        collection.updated(r.nextInt(maxCapacity), 0)
        System.nanoTime() - currentTime
    }

    times.sum * 1.0 / maxRun
  }

  //List
  //pro - keeps ref to tail
  //cons - updating an elem in the middle takes long
  val numberList = (1 to maxCapacity).toList

  //Vector
  //pros - depth is small
  //cons - replace entire 32-element chunk
  val numbersVector = (1 to maxCapacity).toVector


  println(getWriteTime(numberList))
  println(getWriteTime(numbersVector))

}
