# Part 4
## Implicit
- A method can have an implicit parameter list, marked by the implicit keyword at the start of the parameter list.
- If the parameters in that parameter list are not passed as usual, Scala will look if it can get an implicit value of the correct type,
    - and if it can, pass it automatically.
    - *Inject*
- Scala will first look for implicit definitions and implicit parameters that can be accessed directly (without a prefix) at the point the method with the implicit parameter block is called.
```
  // probably in a library
class Prefixer(val prefix: String)
def addPrefix(s: String)(implicit p: Prefixer) = p.prefix + s

  // then probably in your application
implicit val myImplicitPrefixer = new Prefixer("***")
addPrefix("abc")  // returns "***abc"
```
- Then it looks for members marked implicit in all the companion objects associated with the implicit candidate type.
- Implicit scope priority
       - normal scope = LOCAL SCOPE
       - imported scope
       - companions of all types involved in the method signature
         - List
         - Ordering
         - all the types involved = A or any super types
       - `def sorted[B >: A](implicit ord: Ordering[B]): List[B]`

## Implicit conversion
- When the compiler finds an expression of the wrong type for the context, it will look for an implicit `Function`
    -  if an A is required and it finds a B, it will look for an implicit value of type B => A in scope
        - (it also checks some other places like in the B and A companion objects, if they exist)
            ```
            implicit def doubleToInt(d: Double) = d.toInt
            val x: Int = 42.0
            ```
            **Same as**
            ```
            def doubleToInt(d: Double) = d.toInt
            val x: Int = doubleToInt(42.0)
            ```