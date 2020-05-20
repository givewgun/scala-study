# Part 2

## Partial Function
- PF as things that can take params as only a subset of Domain
```aidl
  //partial function
  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  } //partial function value (like case but sweeter)
```

- must have `apply()` and `isDefinedAt()` method
```aidl
val aManualFussyFunction = new PartialFunction[Int, Int] {
    override def apply(v1: Int): Int = v1 match {
      case 1 => 42
      case 2 => 444
      case 5 => 66666
    }
    
    override def isDefinedAt(x: Int): Boolean =
      x ==1 | x == 2 | x == 5
}
```

- use as a literal runs on pattern matching
- Utilities
    - isDefinedAt: check if value is in the PF case
    - lift : change to full func -> return None if not match
    ```
      val lifted = aPartialFunction.lift // Int => Option[Int]
      println(lifted(1)) //Some(42)
      println(lifted(3333)) //None
    ```
    - orElse : Chained
    
    ``
## Collection as a function
- basically many are partial functions
- Seq
    - defined on the domain `[0,..., length -1]`
    - partial func `PartialFunc[Int, A]` where `Int` is the index
- Map
    -also `PartialFunction[A, B]`
        - `apply(key: A):B` //key calling
    - defined on the domains of its key (`A`)
    
## Currying and PAF and ETA-expansion
- curried function =  f that return other f as result
```aidl
 val superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3) // Int => Int = y => 3 + y
  println(add3(5))
  println(superAdder(3)(5)) //curried function
```
- curried method
```aidl
 def curriedAdder(x: Int)(y: Int): Int = x + y

  val add4: Int => Int = curriedAdder(4) //must state type
```
- ETA expansion -> compiler convert method into a function
    - "lifting"
```aidl
def inc(x: Int) = x + 1
List(1,2,3).map(inc) // Compiler convert using ETA to x => inc(x)

val add5 = curriedAdder(5) _ // _ convert this to Int => Int
```
- Underscore are very powerful => ETA manual from method
```aidl
def concatenator(a: String, b: String, c: String) = a + b + c
def insertName = concatenator("Hello I'm ", _: String, "how are you") // x: String => concatenator(hello, x, howareu)
println(insertName("Gun"))

val fillInBlank = concatenator("Hello", _: String, _: String) // (x, y) => concatenator("hello", x, y)
println(fillInBlank("Gun", "Nug"))
```
        