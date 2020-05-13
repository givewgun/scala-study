# Part 2

##Basics
- constructor
    - className(x: type, val y: type){}
    - auxiallary constructor -> call defaut constructor but with different parameters
        - should use default val in main constructor
    - class parameters != FIELDS
    - need to add val/var to class parameters to covert it to field
- field is prefered to be val -> immutibility -> modify = return new Instance!!!!!!!!

##Method
- infix notation = operator notation (only 1 parameter method)
    - syntactic sugar
    - println(mary likes "Inception")
- "operators" in Scala
    - operator like in math = freedom in naming
    - println(1 + 2) = println(1.+(2))
    - ALL OPERATORS ARE METHOD
- prefix notation
    - unary_ prefix only works with - + ~ !
    -  -1 equivalent with 1.unary_-
- postfix notation
    - function that doesn't receive any parameters
    - rarely used
    - (mary.isAlive) = (mary isAlive)
    - import scala.language.postfixOps
- apply
    - println(mary.apply())
    - println(mary()) //call the apply() method in class
    - can have parameters

##Objects
- Scala doesn't have "static" value/method
- Scala objects
    - are in theier own class
    - are the only instance
    - singleton pattern in one line
- Scala companions
    - same class and object name in same scope
    - can access each other private members
- Scala application
    - def main(args: Array[String]): Unit = {....}
    - same as extends App
   
##Inheritance & Trait
- Basics
    - Single class inheritance
    - overriding
    - call parents constructors
    - access : private: protected, default(none = public)
    -sealed -> in only one file
    
- traits
    - traits vs abstract classes
         - traits do not have constructor parameters
         - multiple traits may be inherited by the same class
- traits = behavior, abstract class = type of thing
- Use object with abstract to help -> linked list (empty list)  

## Generics
- "So that we can be aware of input and return type"
    - like MyList[A] - MyList of (type A)
    - transform[A, MyList[A]] -> transform (type A) into (Mylist of type A) ~ [type-in,type-out]
- Define the **generic** type using square brackets `[A]` or `[A, B]`
- `Trait`s and methods can also be **generic**
- `Covariance` in scala is noted with a `[+A]` **generic** type. This allows to assign a subclass type to a parent class type.
- `Invariance` is simple `[A]` **generic** type. This does not allow to substitue a subclass
- `Contravariance` is noted by `[-A]` allows to assign a parent class
- `Bounded types` allows the super-class or sub-class to be substitued
    - Example for sub-class: only sub-class of `Animal` is allowed.
        ```
        class Cage[A <: Animal](animal: A)
        val cage = new Cage(new Dog)
        ```
    - Example for super-class: only a parent class of `Animal` is allowed.
        ```
        class Cage[A >: Animal](creature: A)
        val cage = new Cage(new Creature)
        ```
 ##Anonymous class
- anonymous class -> new ClassName {....} -> compiler create a new anonymous class (not the same name that was implemented prior)
- on the spot implementation / overriding
- must provide same paremters as original class constructor
- can be used for abstract or simple class
- implement all abstract method or field

##Case Class
- use in Collections-like
- AKKA!!!!
- class parameters are fields
- sensible toString
- equals and hashCode implemented Out of The Box
- CCs have handy copy method
- CCs have companion object 
    - use companion objects apply as constructor
- CCs are serializable!!!!!!
- CCs have extractor patterns
    - CCs can be used in PATTERN MATCHING

##Exceptions
- extends Throwable
    - Exceptions or Error
- Creatable
- try catch finally -> expression


    
  