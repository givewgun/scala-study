import scala.annotation.tailrec
import scala.util.Random

//like a tree set
trait MySet[+A] {

  def contains[B](elem: B): Boolean
  def add[B >: A](elem: B): MySet[B]
  def remove[B >: A](elem: B): MySet[B]
  def intersect[B >: A](anotherSet: MySet[B]): MySet[B]
  def union[B >: A](anotherSet: MySet[B]): MySet[B]
  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(pred: A => Boolean): MySet[A]

}

object EmptySet extends MySet[Nothing] {
  override def contains[B](elem: B): Boolean = false

  override def add[B >: Nothing](elem: B): MySet[B] = new NonEmptySet[B](elem, EmptySet, EmptySet)

  override def remove[B >: Nothing](elem: B): MySet[B] = this


  override def intersect[B >: Nothing](anotherSet: MySet[B]): MySet[B] = this

  override def union[B >: Nothing](anotherSet: MySet[B]): MySet[B] = anotherSet

  override def map[B](f: Nothing => B): MySet[B] = this

  override def flatMap[B](f: Nothing => MySet[B]): MySet[B] = this

  override def filter(pred: Nothing => Boolean): MySet[Nothing] = this
}

class NonEmptySet[+A](current: A, left: MySet[A], right: MySet[A]) extends MySet[A] {

  override def contains[B](elem: B): Boolean = {
    if (elem == current) true
    else left.contains(elem) || right.contains(elem)
  }

  override def add[B >: A](elem: B): MySet[B] = {
    if (current == elem) this
    else {
      val isLeft = new Random().nextBoolean()
      if (isLeft) new NonEmptySet(current, left.add(elem), right)
      else new NonEmptySet(current, left, right.add(elem))
    }
  }


  override def remove[B >: A](elem: B): MySet[B] = {
    //procedural left to right
    if (current == elem) left union right
    else if (left.contains(elem)) new NonEmptySet(current, left.remove(elem), right)
    else new NonEmptySet(current, left, right.remove(elem))
  }

  override def intersect[B >: A](anotherSet: MySet[B]): MySet[B] = anotherSet match {
    case EmptySet => EmptySet
    case s: MySet[B] => this.filter((elem: B) => s.contains(elem))
  }

  override def union[B >: A](anotherSet: MySet[B]): MySet[B] =
    ((left union right) union anotherSet).add(current)
//    //stack overflow!
//    anotherSet match {
//    case EmptySet => this
//    case s: MySet[B] => s.add(current).union(left).union(right)
//  }

  override def map[B](f: A => B): MySet[B] = {
    new NonEmptySet(f(current), left.map(f), right.map(f))
  }

  override def flatMap[B](f: A => MySet[B]): MySet[B] = {
    f(current) union left.flatMap(f) union right.flatMap(f)
  }

  override def filter(pred: A => Boolean): MySet[A] = {
    if (pred(current)) new NonEmptySet(current, left.filter(pred), right.filter(pred))
    else left.filter(pred) union right.filter(pred)
  }

}

object MySet {
  /*
    val s = MySet(1,2,3) = buildSet(seq(1,2,3), [])
   */
  def apply[A](values: A*): MySet[A] = {

    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] =
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc.add(valSeq.head))

    buildSet(values.toSeq, EmptySet)
  }
}
