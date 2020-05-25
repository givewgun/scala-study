package lectures.part4Implicits

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
object MagnetPattern extends App {

  //solve method overloading problem
  class P2PRequest
  class P2PResponse
  class Serializer[T]
  trait Actor {
    def receive(statusCode: Int): Int
    def receive(request: P2PRequest): Int
    def receive(request: P2PResponse): Int
    def receive[T: Serializer](message: T): Int
    def receive[T: Serializer](message: T, statusCode: Int): Int
    def receive(future: Future[P2PRequest]): Int
//    def receive(future: Future[P2PResponse]): Int //type erasure
    //lots of overload
  }
  /*
    Problems
    1 - type erasure (generic type is erased at compile time)
    2 - lifting doesn't work for all overlaod
      - val receiveFV = receive _ ???? what is _
    3 - code duplication
    4 - type inference and default args
      - actor.receive(?!)
   */

  //solution
  trait MessageMagnet[Result] {
    def apply(): Result
  }

  def receive[R](magnet: MessageMagnet[R]): R = magnet()

  implicit class FromP2PRequest(request: P2PRequest) extends MessageMagnet[Int] { //extends with intended result type
    override def apply(): Int = {
      //hold logic for handling P2PRequest
      println("P2PRequest")
      42
    }
  }
  implicit class FromP2PResponse(request: P2PResponse) extends MessageMagnet[Int] {
    override def apply(): Int = {
      //hold logic for handling P2PRequest
      println("P2PResponse")
      22
    }
  }

  receive(new P2PRequest)
  receive(new P2PResponse)

  //using magnets
  //1. no more type erasure problem
  implicit class FromResponseFuture(future: Future[P2PResponse]) extends MessageMagnet[Int] {
    override def apply(): Int = 2
  }

  implicit class FromRequestFuture(future: Future[P2PRequest]) extends MessageMagnet[Int] {
    override def apply(): Int = 3
  }

  println(receive(Future(new P2PResponse)))
  println(receive(Future(new P2PRequest)))

  //2 - lifting works
  trait MathLib {
    def add1(x: Int): Int = x + 1
    def add1(s: String): Int = s.toInt + 1
    //add1 overload
  }

  //magnetize
  trait AddMagnet {
    def apply(): Int
  }

  def add1(magnet: AddMagnet): Int = magnet()

  implicit class AddInt(x: Int) extends AddMagnet {
    override def apply(): Int = x + 1
  }

  implicit class AddString(s: String) extends AddMagnet {
    override def apply(): Int = s.toInt + 1
  }

  val addFV = add1 _ //compiler imply type for us cuz we didn't state the type for the trait (unlike receive)
  println(addFV(1))
  println(addFV("3"))

  /*
    Drawback
    1 - Very verbose
    2 - harder to read
    3 - can't name or place default args
    4 - call by name doesn't work correctly (side effects)
   */

  //Drawback #4
  class Handler {
    def handle(s: => String) = {
      println(s)
      println(s)
    }
    //other overloads
  }

  //we try to magnetise
  trait HandleMagnet {
    def apply(): Unit
  }

  def handle(magnet: HandleMagnet) = magnet()

  implicit class StringHandle(s: => String) extends HandleMagnet {
    override def apply(): Unit = {
      println(s)
      println(s)
    }
  }

  def sideEffectMethod(): String = {
    println("SIDE EFFECT")
    "SIDEEEEEDDDEE"
  }

  handle(sideEffectMethod()) //"SIDE EFFECT" * 2 (whole method is converted to a magnet class)
  handle({
    println("SIDE EFFECT") //print only once cuz
    new StringHandle("SIDEEEEEDDDEE") //only this is convert to a magnet class
  })
}
