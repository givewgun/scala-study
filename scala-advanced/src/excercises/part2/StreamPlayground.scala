package excercises.part2

import scala.annotation.tailrec

/*
    implement a lazily evaluated, singly liked STREAM of elements

    MyStream.from(1)(x => x + 1) = stream of natural numbers (infinite)
    naturals.take(30).forEach(println) //lazily evaluated of the first 100 naturals (finite stream)
    naturals.forEach(println) //CRASH
    naturals.map(_ * 2) //stream of even numbers (potentially infinite)
   */


abstract class MyStream[+A] {
  def isEmpty: Boolean
  def head: A
  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B] //prepend operator
  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B]

  def forEach(f: A => Unit): Unit
  def map[B](f: A => B): MyStream[B]
  def flatMap[B](f: A => MyStream[B]): MyStream[B]
  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A] // take the first n elements out of this stream
  def takeAsList(n: Int): List[A] = take(n).toList()

  /*
    [1 2 3].toList([])
    [2 3].toList([1])
    [3].toList([2 1]
    [].toList([3 2 1])
    = [1 2 3] //need reverse
   */
  @tailrec
  final def toList[B >: A](acc: List[B] = Nil): List[B] = {
    if (isEmpty) acc.reverse //cuz see up
    else tail.toList(head :: acc)
  }
}

object EmptyStream extends MyStream[Nothing] {
  override def isEmpty: Boolean = true

  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyStream[Nothing] = throw new NoSuchElementException

  override def #::[B >: Nothing](element: B): MyStream[B] = new Cons(element, this)

  override def ++[B >: Nothing](anotherStream: => MyStream[B]): MyStream[B] = anotherStream

  override def forEach(f: Nothing => Unit): Unit = () //no element to apply f

  override def map[B](f: Nothing => B): MyStream[B] = this

  override def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

  override def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  override def take(n: Int): MyStream[Nothing] = this

}

class Cons[+A](h: A, t: => MyStream[A]) extends MyStream[A] { //tail lazily evaluated
  override def isEmpty: Boolean = false

  override val head: A = h

  override lazy val tail: MyStream[A] = t // combining by-name in the construct with lzay = call by need

  /*
    val s = new Cons(1, EmptyStream)
    val prepended = 1 #:: s = new Cons(1, s) // EmptyStream is not evaluate yet cuz s is lazy evaluated
   */
  override def #::[B >: A](element: B): MyStream[B] = new Cons(element, this)

  override def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = new Cons(head, tail ++ anotherStream) // tail ++ .. still preserve lazy

  override def forEach(f: A => Unit): Unit = {
    f(head)
    tail.forEach(f)
  }

  /*
    s = new Cons(1, ?)
    mapped = s.map(_ + 1) = new Cons(2, s.tail.map(_ + 1)) //s.tail.map(_ + 1) still not evaluate ey
        .... mapped.tail -> this will evaluated the preserved one
   */
  override def map[B](f: A => B): MyStream[B] = new Cons(f(head), tail.map(f)) //preserves lazy eval

  override def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f)

  override def filter(predicate: A => Boolean): MyStream[A] =
    if (predicate(head)) new Cons(head, tail.filter(predicate)) //preserve
    else tail.filter(predicate) // atmost the first elem will be evaluated (still preserves)

  override def take(n: Int): MyStream[A] = {
    if (n <= 0) EmptyStream
    else if (n == 1) new Cons(head, EmptyStream)
    else new Cons(head, tail.take(n - 1))
  }


}

object MyStream {
  //factory method
  def from[A](start: A)(generator: A => A): MyStream[A] =
    new Cons(start, MyStream.from(generator(start))(generator)) //last huy is lazily eval so no stack overflow ...
}



object StreamPlayground extends App {
  val naturals = MyStream.from(1)(_ + 1)
  println(naturals.head)
  println(naturals.tail.head)
  println(naturals.tail.tail.head)

  val startFrom0 = 0 #:: naturals //naturals.#::(0)

  startFrom0.take(10000).forEach(println)

  //map flatMap
  println(startFrom0.map(_ * 2).take(100).toList())
  println(startFrom0.flatMap(x => new Cons(x + 1, EmptyStream)).take(10).toList()) //flatMap Stackoverflow !!!
  // ++ nee to use by-name to preserve lazy cuz if not the flatMap will evaluate the tail.flatMap(f) first then
  //evaluate big flatmap that will evaluate the tail..... infinite => boom
  //List(0,1,1,2,2,3,3,4,4,5)
  //println(startFrom0.filter(_ < 10).toList()) //filter stack overflow -> cuz startFrom0 infinite
  // -> filter -> is it still finite or infinite -> where does it ends? -> ehhhhh
  println(startFrom0.filter(_ < 10).take(10).toList()) //OK

  /*
    1. stream of Fibbonacci
    2 stream of primes (Erastosthenes's seive)
      [2, 3 4 ...]
      filter out divisible by 2
      [2 3 5 7 9 .. ]
      filter out divisible by 3
      [2 3 5 7 11 13 ...]
      ...
   */

  /*
    [first, [ ...
    [first, fibo(second, first + second)
   */
  def fibonacci(first: Int, second: Int): MyStream[Int] = {
    new Cons[Int](first, fibonacci(second, first + second))
  }

  println(fibonacci(1, 1).take(100).toList())

  //erasthosthenes
  /*
    [2 3 4 5 6 7 8 9 ..]
    [2 3 4 5 9 11 13 ..]
    [2 erastosthenes applied to (number filtered by n % 2 !== 0)]
    [2 3 erastosthenes applied to ([5 7 9 11 ...] filtered by n % 3 !== 0)
   */
  def eratosthenes(numbers: MyStream[Int]): MyStream[Int] =
    if (numbers.isEmpty) numbers
    else new Cons(numbers.head, eratosthenes(numbers.tail.filter(n => n % numbers.head != 0)))

  println(eratosthenes(MyStream.from(2)(_ + 1)).take(100).toList())


}
