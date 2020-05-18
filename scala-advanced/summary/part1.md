# Part 1
## More Syntactic sugar!

- Single Parameter functions
    - We can call a single parameter function using `{}`:
```
println {
    /// some code
    "Hello, Scala~"
}
```

- Single abstract function
    - To override a method in an abstract class having a single method, we can use a **lanbda** expression:
```
val someInstance: SingleMethodAbstractClass = (x: Type) => { // code }
```

- Colon and Hashtag
    - Last character of the `::` or `#::` (for **stream**) operator decides of the associativity. :
```
1 :: 2 :: 3 :: List(4, 5)
// is equivalent to 
1 :: 2 :: List(4, 5).::(3)
// and by extension
List(4, 5).::(3).::(2).::(1)
```

- Backtick function notation
    - A method name can be enclosed in backticks then contain spaces.
```
class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s"$name said $gossip")
    }
val lilly = new TeenGirl("Lilly")
lilly `and then said` "scala is sweet."
```

- More infix types
    - A different notation for specifying types:
```
class Composite[A, B]

// val composite: Composite[Int, String] = ???
val composite: Int Composite String = ???
```

- Update function
    - In mutable collections:
```
val anArray = Array(1,2,3)
anArray(2) = 7
// is re-written to
anArray.update(2, 7)
```

- Setters and getters
    - Provide 2 specific functions to enable getter and setter:
```
class Mutable {
  private var internalMember: Int = 0
  def member = internalMember //getter
  def member_=(value: Int): Unit = internalMember = value //setter
}

val aMutabe = new Mutable
aMutabe.member = 7
```

## Advanced Pattern Matching
- Infix and `Nil`
    - Below would match if the list has only 1 element (i.e. only the head woud have a value, and the tail is `Nil`)
```
val numbers = List(1)
val description = numbers match {
    case head :: Nil => println(s"the only element is $head")
    case _ =>
}
```
- infix pattern only works for 2 things in the pattern
```aidl
 case class Or[A, B](a: A, b: B) //Either
  val either = Or(2, "two")
  val humanDescription = either match {
    case number Or string => s"$number is $string"
  }
```

- Unapply, unapplySeq
    - If a class cannot be pattern matched, use a singleton companion object with an `unapply` function that returns an `Option` deconstructing the base class:
```
  object Person {
    def unapply(person: Person): Option[(String, Int)] = { //match the whole class
      if (person.age < 12) Some((person.name, person.age))
      else None
    }

    def unapply(age: Int): Option[String] = { //match only age
      Some(if (age < 21) "minor" else "major")
    }
  }

  val bob = new Person("Bob", 12)
  val greeting = bob match {
    case Person(n, a) => s"Hi im $n, Im $a"
  }
```

- Return `None` when you don't want a condition to match.
- `Case` classes have a built-in `unapply` function.
    - Use `unapplySeq` to wildcard match:
```
  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }
  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] = {
      if (list.equals(Empty)) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
    }
  }

  val myList: MyList[Int] = new Cons(1, Cons(2, Cons(3, Empty)))
  val decompose = myList match {
    case MyList(1, 2, _*) => "Starting with 1and 2"
    case _ => "something else"
  }
```

- To return a custom type in `unapply`, we need a type that contains two functions:
```
isEmpty; boolean
get: T
```
```aidl
  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }
  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false
      override def get: String = person.name
    }
  }

  println(bob match {
    case PersonWrapper(n) => s"This person name is $n"
    case _ => "what"
  })
```