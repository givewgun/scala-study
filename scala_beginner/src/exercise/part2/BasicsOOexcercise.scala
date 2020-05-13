package exercise.part2

object BasicsOOexcercise extends  App {
  val author = new Writer("Charles", "Dicken", 1812)
  val imposter = new Writer("Gun", "ka", 1997)
  val novel = new Novel("Great Expectation", 1861, author)

  println(novel.authorAge())
  println(novel.isWrittenBy(imposter))

  val counter = new Counter
  counter.inc.print
}

class Writer(val firstName: String,
             val surName: String,
             val year: Int){

  def fullname(): String = return s"$firstName $surName"
}

class Novel(val name: String,
            val yearRelease: Int,
            val author: Writer){

  def authorAge() = yearRelease - author.year

//  def isWrittenBy(author: Writer): Boolean = return author.fullname().equals(this.author.fullname())
  def isWrittenBy(author: Writer) = author.equals(this.author)
  def copy(newReleaseYear: Int): Novel = new Novel(name, newReleaseYear, author)
}
//
class Counter(val count: Int = 0){
  def inc = {
    println("Increment")
    new Counter(count + 1) // immutibility
  }
  def dec = {
    println("Decrement")
    new Counter(count - 1)
  }

  def inc(n: Int): Counter = {
    if (n <= 0) this
    else inc.inc(n-1)
  }
  def dec(n: Int) = {
    if (n <= 0) this
    else inc.inc(n-1)
  }

  def print = println(count)
}
