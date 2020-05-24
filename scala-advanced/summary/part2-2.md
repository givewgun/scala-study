# Part 2.5
## Lazy Evaluation and by-name
- lazy evaluation is like it will only evaluate once if called
```
  lazy val x: Int = throw new RuntimeException

  //lazy delays the evaluation of values
  //x evaluate when call
  //println(x) //crashed

  lazy val y: Int = {
    println("hello")
    12
  }
  println(x) //hello 42
  println(x) //42 (evaluate only onced for the first time)
```
- can be used with short circuit
```
  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }
  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition
  println(if (simpleCondition && lazyCondition) "yes" else "no")
```
- use with by-name parameter to create CALL BY NEED
    - by name (x : => Type) : call x whenever presented in method body
    - use lazy to like evaluate the first time we see the param use -> save it and use it later if see later
        - no need to re evaluate
```
  def byName(n : => Int): Int = {
    lazy val t = n //evaluate once (CALL BY NEED)
    t + t + t + 1
  }
  def retrieveMagicValue = {
    println("wait")
    Thread.sleep(1000)
    42
  }
  println(byName(retrieveMagicValue))
```

## Monads
- Monad is not a class or a trait; it is a concept.
    - construction which performs successive calculations
- An object which covers the other object
- The maximum collections of the Scala are Monads but not all the Monads are collections
- Operations provided by the Monads
    - unit() (return) — e.g, Option.apply : It is like void in Java, it does not returns any data types.
        - a: => A
    - flatMap() (bind) —e.g, Option.flatMap : It is similar to the map() in Scala but it returns a series in place of returning a single component.
        - f: A => Attempt[B]

- https://blog.redelastic.com/a-guide-to-scala-collections-exploring-monads-in-scala-collections-ef810ef3aec3
