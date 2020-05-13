package exercise.part2



//traits and inheritance -> every predicate / transformer has different behavior implementation
trait MyPredicate[-T]{
  def test(element: T): Boolean

}

trait MyTransformer[-A,B]{
  def transform(element: A): B
}

//use covariance +A because we use Nothing (type) thus when we add the type will change
//and the list can use Any type (biggest)
abstract class MyList[+A]{

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String

  override def toString: String = "[" + printElements + "]"

  def map[B](transformer: MyTransformer[A,B]) : MyList[B] //has [B] after map cuz change type A to B

  //need concatenation -> ++ func
  def flatMap[B](transformer: MyTransformer[A,MyList[B]]): MyList[B]


  def filter(predicate: MyPredicate[A]): MyList[A]

  //concatnation
  def ++[B >: A](list: MyList[B]): MyList[B] //use B cuz covariant error



}

// empty singleton object like static empty list casue all is the same
case object EmptyList extends MyList[Nothing]{
  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](element: B): MyList[B] = new Cons(element, EmptyList) //not this cuz EmptyList is singleton instance (object)

  override def printElements: String = ""

  def map[B](transformer: MyTransformer[Nothing,B]) : MyList[B] = EmptyList

  def flatMap[B](transformer: MyTransformer[Nothing,MyList[B]]): MyList[B] = EmptyList


  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = EmptyList

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
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
  override def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    new Cons(transformer.transform(h), t.map(transformer))
  }

  /*
  [1,2,3].filter(n % 2 == 0) =
    [2,3].filter(n % 2 == 0) =
    = new Cons(2, [3].filter(n % 2 == 0)
    = new Cons(2, EmptyList.filter(n % 2 == 0))
    = new Cons(2, EmptyList)
   */

  override def filter(predicate: MyPredicate[A]): MyList[A] = {
    //test if head satisfy predicate -> check tail
    if(predicate.test(h)) new Cons(h, t.filter(predicate))
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
  override def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
    transformer.transform(h) ++ t.flatMap(transformer)
  }
}

object ListTest extends App{
  val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, EmptyList))) //don't need to state [type] can be inferred
  val cloneListOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, EmptyList))) //don't need to state [type] can be inferred

  val anotherListOfIntegers: MyList[Int] = new Cons(4, new Cons(5, EmptyList))
  val listOfAny: MyList[Any] = new Cons("Gun", new Cons("SSD", new Cons(3, EmptyList)))

  println(listOfAny.toString)

  println(
    listOfIntegers.map(new MyTransformer[Int, Int] {
      override def transform(elem: Int): Int = elem * 2
    }).toString
  )

  println(listOfIntegers.filter(new MyPredicate[Int] {
    override def test(element: Int): Boolean = (element % 2 == 0)
  }))


  println((listOfIntegers ++ anotherListOfIntegers).toString)
  println(listOfIntegers.flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(element: Int): MyList[Int] = new Cons(element, new Cons(element + 1, EmptyList))
  }))

  println(listOfIntegers == cloneListOfIntegers) //power of case classes

}