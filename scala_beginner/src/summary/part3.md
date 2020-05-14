# Part 3 Function
##Confusinon clearance
-concept func is like apply() method in an instance
- confuse about curries and labda when how???
    - the end of => is the return type
    - view => as another enter line 
        - then that line becomes .apply param=val (before the val)
        ```
        def toCurry(f: (Int, Int) => Int): (Int => Int => Int) =
            x => y => f(x,y)
        ```
        -basically is
        ```toCurry(f) = 
            apply(x)
                .apply(y)
                    f(x,y)
        ```   

## Basic 
- pass func as params
- use func as value
- problem cuz JVM designed for Java -> OOP
    - sol -> funcs are all instances
    - traits up to 22 params
    - syntactic sugar func types
        ```
            Function2[Int,String, Int] === (Int,String) => Int
        ```
    - mainly used apply(params...) , thus not having to explicitly state types when use
    - Higher-order functions receive a function as parameter, or return a function as a result
    ```
      val superAdder: (Int) => Function1[Int, Int] = (x: Int) => (y: Int) => x + y
      val superAdder: Function1[Int, Function1[Int,Int]] = new Function1[Int, Function1[Int,Int]]{
        override def apply(x: Int): Function1[Int,Int] = new Function1[Int,Int]{
          override def apply(y: Int): Int = x + y
        }
      }
        
      println(superAdder(3)(4)) //curried function
    ```

##Anonymous function
- Lambda functions are anonymous functions that use the syntactic sugar for Function1 ... Function22:
    ```
      val doubler: Int => Int = (x) => x * 2 //state the type of ret -> compiler will check for u

      //multiple params
      val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b
    ```
- Lambdas must be called with paranthesis, otherwise the value is the function itself.
    - basically func() -> behave like calling apply() of teh func
- Another notation is to use curly braces:
    ```
        val doubler = { (x: Int) =>
            x * 2
        }
    ```
- underscore _ placeholder to replace x => x or the same sequence 
    - like val add: Int => Int = _ + 1 // is x + 1
    - ```
      val added: (Int, Int) => Int = (a, b) => a + b
      val added: (Int, Int) => Int = _ + _

        ```
## Higher-order functions and Curried
- Recap
    - A curried function is a function that returns a function, and allows to be called with list of parameters:
    ```
    superAdder(3)(4)
    ```
- higher order function (HOF) is a function that takes a function as parameter or returns a function as a result.
- A function with a list of parameter is a curried function. The type for the smaller functions must have type defined.
- ***Curried Function*** === *function that returns a function, and allows to be called with list of parameters*
```aidl
//helpful for define helper func to use later on various value
 
  val superAdder: (Int => (Int => Int)) = (x: Int) => (y: Int) => x + y
  val add3 = superAdder(3) // y => 3 + y
  println(add3(10))
  println(superAdder(3)(10))

  //function with multiple parameter list
  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  //to use curried function -> define type of smaller func that use it!
  val standardFormat: (Double => String) = curriedFormatter("%4.2f") //use later for x (apply)
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

  println(standardFormat(Math.PI))
  println(preciseFormat(Math.PI))
```
- Can write curried like APi like
```
// have service that will take only what val you want to put in -> do higher-func core for you
  // ntb(f,n) = x => f(f(f(...f(x))))
  //increment10 = ntb(plusOne, 10) = x => plusOne(plusOne(...(x)))
  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) ={
    // new labda to receive another parameter (real one)
    if(n <= 0) (x: Int) => x // 0 times
    else (x: Int) => nTimesBetter(f, n-1)(f(x)) // remember, f is a function!!!
  }

  val plus10 = nTimesBetter(plusOne, 10)
  println(plus10(1))
```