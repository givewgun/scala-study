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
        