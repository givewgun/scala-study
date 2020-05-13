package lectures.part2oop

object Generics extends App {

  class MyList[+A] {
    def add[B >: A](element: B): MyList[B] = ???
    /*
    A = Cat
    B = Dog
     */
  }

  class MyMap[Key, Value]

  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]

  //generic methods
  //companions
  object MyList {
    def empty[A]: MyList[A] = ???
  }
  val emptyListOfIntegers = MyList.empty[Int]

  //variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  //1. yes List[Cat] extends List[Animal] = COVARIANCE
  class CovariantList[+A]
  val animal:Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  //animalList.add(new Dog) ????? HARD QUESTION -> return a list of animal

  //2. NO = INVARIANCE
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]

  //3. Hell, NO! CONTRAVARIANCE
  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  //Bounded types
  class Cage[A <: Animal](animal: A) // A is subtype of Animal upperbound <: , lowerbound A >: Animal -> A superclass animal
  val cage = new Cage(new Cat)
  








}