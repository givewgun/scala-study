# Collection

## Map Flatmap Filter For
- for-comprehension is a notation that makes it more readable when a series of map and flatMap are cascaded
- yield is like remembering
    - will return like a collection or smth
    - (plain for() won't remenber shit)
```
//iteration
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
```
- also possible to guard any elem in list by adding `if` whih is basicall `.filter()`
- A for-loop can be written
```aidl
for{
    n <- numbers //for n in numbers!!
  } println(n)
  // numbers.map(n => println(n))
//better
for(n <- 1 to 10){
    println(n)
}
```

## Collection overview
- types
    - mutable collection can be updated in place
    - immutable can never change!
        - like MyList
- Traversable = base traits for all collection
## Mutable
- quite similar to *scala.collections.mutable*
## Immutable Collections
- found in *scala.collections.immutable* packages

### List Seq Array vec
- The `List` two sub-types are `Nil` **object** and `::` **class**. Using a value followed by `::` then a `List` is equivalent to calling the `apply` method on a `List`:
```
val prepended = 42 :: aList
val prependedToo = ::.apply(42, aList)
```
- `5 :: List(1,2,3)` and `5 +: List(1,2,3)` 
    - quite the same
    - : onside of list
```
val anotherPrepended = 42 +: aList :+ 56
val anotherPrependedToo = aList.prepended(42).appended(56)
```

`List` have constant execution time for `head`, `tail` and `isEmpty`  
`List` have linear execution time for `length`, `reverse`...  

`Vector` have effectively constant execution time  read and write: O(log32(n))

### Map tuples
`Tuple`s are finite ordered kind-of `List`. It can be initialized with full notation or short notation:
```
val aTuple = new Tuple2(2, "Hello, Scala!")
// or
val anotherTuple = Tuple2(2, "Hello, Scala!")
// or even 
val yetAnotherTuple = (2, "Hello, Scala!")
```

There can be up to 22 elements in a `Tuple`

`Map`s are used to associate **key**s to **value**s.

We can use the syntactic sugar `a -> b` to link a **key** to a **value**.

Accessing an element of a `Map` with a non-existant `key` will result in a `NoSuchElementException`. It is possible to provide a default value:
```
val aMap = Map("John" -> 123, "jim" -> 456).withDefaultValue(-1)
```
`Map` being immutable, to add a `tuple`:
```
val newMap = oldMap + (key,  value)
```
Using `groupBy` on a `List` creates a `map`



- visit cheat sheet

## Experiment
```

val a = Some(List(List(1,2,3,4), List(5,6,7,8)))
  .foldLeft(List[List[Int]]())((acc, x) => acc ++ x)
//
val b = a.map(l => l.flatMap(x => List(x, x + 5)))

//b is List(List(1, 6, 2, 7, 3, 8, 4, 9), List(5, 10, 6, 11, 7, 12, 8, 13)): List[List[Int]]

val flatter = for {
  l <- b
  elem <- l
} yield elem + 1

//flatter is List(2, 7, 3, 8, 4, 9, 5, 10, 6, 11, 7, 12, 8, 13, 9, 14): List[Int]
```
