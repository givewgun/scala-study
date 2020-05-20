package excercises.part2

object CurriesAndPAF extends App {



  //1
  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  val simpleFormat = curriedFormatter("%4.2f") _ //lift
  val seriousFormat = curriedFormatter("%8.6f") _

  println(numbers.map(simpleFormat))
  println(numbers.map(curriedFormatter("%4.2f"))) //compiler do ETA-formatting for us

  /*
    2. difference between
      - functions vs methods
      - parameter: by name vs 0-lambda

   */
  def byName(n: => Int) = n + 1 //by name parameter
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def paramMethod(): Int = 42 // val paramMethod = () => 42

  /*
    calling byName and byFunction
      -int
      -method
      -parammMethod
      -lambda
      -PAF
   */
  byName(23) //OK
  byName(method) //OK
  byName(paramMethod()) //OK
  byName(paramMethod) // Ok but bewarre == byname(paramMethod()) !not calling byName like a HOF
  //by name != ny function parameter
  //byName(() => 42) NOT OK
  byName((() => 42)()) //OK cuz apply lambda and CALLED IT
  //byName(paramMethod _ ) NOT OK

  //byFunction(45) // Not OK
  //byFunction(method) //NOT Ok!!!!!!! does not do ETA here
  byFunction(paramMethod) //OK compiler does ETA-expansion
  byFunction(() => 46) //OK
  byFunction(paramMethod) //also works
}

