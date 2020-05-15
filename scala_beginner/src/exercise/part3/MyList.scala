package exercise.part3

import lectures.part2oop.Generics.MyList

import scala.annotation.tailrec

//use covariance +A because we use Nothing (type) thus when we add the type will change
//and the list can use Any type (biggest)
abstract class MyList[+A]{

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
//  def addList[B >: A](list: MyList[B]): MyList[B]
  def printElements: String

  override def toString: String = "[" + printElements + "]"

  def getLastElem: A
  //higher-order functions
  //receive func as param || return other func as result
  def map[B](transformer: A => B) : MyList[B] //has [B] after map cuz change type A to B

  //need concatenation -> ++ func
  def flatMap[B](transformer: A => MyList[B]): MyList[B]

  def filter(predicate: A => Boolean): MyList[A]

  //concatnation
  def ++[B >: A](list: MyList[B]): MyList[B] //use B cuz covariant error

  /* HOFs
    1.Expand MyList
      - forEach method(A => Unit)
      [1,2,3].forEach(x => println(x)

      - sort function ((A,A) => Int) => MyList
      [1,2,3].sort((x,y) => y - x) => [3,2,1]

      -zipWith(list, (A,A) => B) => MyList[B]
      [1,2,3].zipWith([4,5,6], x * y) => [4,10,18]

      -fold(start)(function) => aValue
      [1,2,3].fold(0)(x + y) = 6
   */
  def forEach(f: A => Unit): Unit
  def sort(compare: (A,A) => Boolean): MyList[A]
  def zipWith[B,C](list: MyList[B], zip: (A,B) => C): MyList[C]
  def fold[B](start: B)(operator: (B,A) => B): B




}

// empty singleton object like static empty list casue all is the same
case object EmptyList extends MyList[Nothing]{

  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](element: B): MyList[B] = new Cons(element, EmptyList) //not this cuz EmptyList is singleton instance (object)

//  override def addList[B >: Nothing](list: MyList[B]): MyList[B] = list

  override def printElements: String = ""

  def map[B](transformer: Nothing => B) : MyList[B] = EmptyList

  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = EmptyList


  def filter(predicate: Nothing  => Boolean): MyList[Nothing] = EmptyList

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  override def forEach(f: Nothing => Unit): Unit = ()

  override def sort(compare: (Nothing, Nothing) => Boolean): MyList[Nothing] = EmptyList

  override def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] = {
    if(!list.isEmpty) throw new RuntimeException("lists do not have the same length")
    else EmptyList
  }

  override def fold[B](start: B)(operator: (B, Nothing) => B): B = start

  override def getLastElem: Nothing = head

}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A]{
  override def head: A = h

  override def tail: MyList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](element: B): MyList[B] = new Cons(element, this)

  override def printElements: String = {
    if(t.isEmpty) "" + head
    else h + " " + t.printElements
  }


  /*
  [1,2,3].map(n * 2)
    = new Cons(2, [2,3].map(n*2))
    = new Cons(2, new Cons(4, [3].map(n*2)))
    = new Cons(2, new Cons(4, new Cons(6, EmptyList.map(n*2))))
    = new Cons(2, new Cons(4, new Cons(6, EmptyList)))
   */
  override def map[B](transformer: A => B): MyList[B] = {
    new Cons(transformer(h), t.map(transformer))
  }

  /*
  [1,2,3].filter(n % 2 == 0) =
    [2,3].filter(n % 2 == 0) =
    = new Cons(2, [3].filter(n % 2 == 0)
    = new Cons(2, EmptyList.filter(n % 2 == 0))
    = new Cons(2, EmptyList)
   */


  override def filter(predicate: A => Boolean): MyList[A] = {
    //test if head satisfy predicate -> check tail
    if(predicate(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)
  }
  /*
  [1,2] ++ [3,4,5]
  = new Cons(1, [2] ++ [3,4,5])
  = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
  = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5, EmptyList))

   */
  override def ++[B >: A](list: MyList[B]): MyList[B] = new Cons(h, t ++ list)
  /*
  [1,2].flatMap(n => [n, n+1])
    = [1,2] ++ [2].flatMap(n => [n, n+1])
    = [1,2] ++ [2,3] ++ EmptyList.flatMap(n => [n, n+1])
    = [1,2] ++ [2,3] ++ EmptyList
    ~ [1,2,2,3]
   */
  override def flatMap[B](transformer: A => MyList[B]): MyList[B] = {
    transformer(h) ++ t.flatMap(transformer)
  }

  override def forEach(f: A => Unit): Unit = {
    f(h)
    t.forEach(f)
  }

  override def sort(compare: (A, A) => Boolean): MyList[A] = {
    //insertion sort
    def insert(x: A, sortedList: MyList[A]): MyList[A] = {
      if(sortedList.isEmpty) new Cons(x, EmptyList)
      else if(compare(x, sortedList.head)) new Cons(x, sortedList) //x is the smallest default x < y
      else new Cons(sortedList.head, insert(x, sortedList.tail))
    }
    //need to implement addList method cuz add is append to head and only take element!
//    @tailrec
//      def insert(x: A, sortedList: MyList[A], accumList: MyList[A]): MyList[A] = {
//        if(sortedList.isEmpty) accumList.add(x) //cuz add = append at head
//        else if(compare(x, sortedList.head)) sortedList.add(x) //x is the smallest default x < y
//        else insert(x, sortedList.tail, accumList.add(sortedList.head))
//      }

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  override def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] = {
    if(list.isEmpty) throw new RuntimeException("list do not have the same length")
    else new Cons(zip(h, list.head), t.zipWith(list.tail, zip))
  }

  override def fold[B](start: B)(operator: (B, A) => B): B = {
    t.fold(operator(start, h))(operator)
  }

  override def getLastElem: A = {
    @tailrec
    def getlastElemHelper(list: MyList[A]): A = {
      if(list.tail.equals(EmptyList)) list.head
      else getlastElemHelper(list.tail)
    }
    getlastElemHelper(this)
  }
}

object ListTest extends App{
  val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, EmptyList))) //don't need to state [type] can be inferred
  val cloneListOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, EmptyList))) //don't need to state [type] can be inferred

  val anotherListOfIntegers: MyList[Int] = new Cons(4, new Cons(5, EmptyList))
  val listOfStrings: MyList[String] = new Cons("Gun", new Cons("SSD", new Cons("SSSS", EmptyList)))


  println("last elem", listOfIntegers.add(5).getLastElem)

  println(listOfStrings.toString)

  println(
    listOfIntegers.map( _ * 2).toString
  )

  println(listOfIntegers.filter((element: Int) => (element % 2 == 0)))


  println((listOfIntegers ++ anotherListOfIntegers).toString)
  println(listOfIntegers.flatMap((element: Int) => new Cons(element, new Cons(element + 1, EmptyList))))

  println(listOfIntegers == cloneListOfIntegers) //power of case classes

  listOfIntegers.forEach(println)
  println(listOfIntegers.sort((x,y)=> y < x)) // descending

  println(listOfIntegers.zipWith[String,String](listOfStrings, _ + "..." + _))
  println(listOfIntegers.fold(0)(_ + _ ))

//  println(new Cons(listOfStrings, listOfStrings).toString)

  //for comprehension cuz what we did support it, map flatMap filter support it
  val combinations = for {  //also an expression
    n <- listOfIntegers
    string <- listOfStrings
  } yield n + " " + string
  println(combinations)


}