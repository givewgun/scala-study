#Part 3 Function
##Basic 
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
##