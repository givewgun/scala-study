package lectures.part3concurrency

import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Random, Success, Try}
//important for futures
//handle thread creation for future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
object FuturesPromises extends App {


  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future { //hold a value computed by someone at some points in time in the "future"
    calculateMeaningOfLife // calculate the meaning of life on ANOTHER thread
  } //(global) which is passed by the compiler

  println(aFuture.value) //Option[Try[Int]]

  println("waiting on the future")
  aFuture.onComplete({ //partial function
    case Success(meaningOfLife) => println(s"the meanign of life is $meaningOfLife")
    case Failure(e) => println(s"Failure: $e")
  }) //called by SOME thread this partial function
  //DON'T MAKE ASSUMPTIONS
  Thread.sleep(2000) //main thread wait of the aFuture to complete

  //mini social network

  case class Profile(id: String, name: String) {
    def poke(anotherProfile: Profile) = {
      println(s"${this.name} poking ${anotherProfile.name}")
    }
  }

  object SocialNetwork {
    //"database"
    val names = Map(
      "fb.id.1-zuck" -> "Mark",
      "fb.id.2-bill" -> "Bill",
      "fb.id.0-dummy" -> "Dummy",
    )

    val friends = Map(
      "fb.id.1-zuck" -> "fb.id.2-bill"
    )

    val random = new Random()

    // API
    def fetchProfile(id: String): Future[Profile] = Future {
      Thread.sleep(random.nextInt(300))
      Profile(id, names(id))
    }

    def fetchBestFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(random.nextInt(400))
      val bfId = friends(profile.id)
      Profile(bfId, names(bfId))
    }
  }

  //client mark poke bill
  val mark = SocialNetwork.fetchProfile("fb.id.1-zuck")
//  //too many bracket, scope misuse etc. -> functional composition
//  mark.onComplete({
//    case Success(markProfile) => {
//      val bill = SocialNetwork.fetchBestFriend(markProfile)
//      bill.onComplete({
//        case Success(billProfile) => markProfile.poke(billProfile)
//      })
//    }
//    case Failure(ex) => ex.printStackTrace()
//  })


  //functional composition for future
  //map, flatMap, filter
  val nameOnTheWall = mark.map(profile => profile.name) //Future[Profile] => Future[String]
  val markBestFriend = mark.flatMap(profile => SocialNetwork.fetchBestFriend(profile)) //Future[Profile] => Future[anotherProfile]
  val zuckBestFriendRestricted = markBestFriend.filter(profile => profile.name.startsWith("Z"))

  //for comprehension
  for {
    mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
    bill <- SocialNetwork.fetchBestFriend(mark)
  } mark.poke(bill)
  Thread.sleep(1000)

  // fallbacks
  val aProfileNoMatterWhat = SocialNetwork.fetchProfile("UNKNOWN").recover({ //recover with PF if fail
    case e: Throwable => Profile("fb.id.0-dummy", "Forever Alone")
  })

  val aFetchedProfileNoMatterWhat = SocialNetwork.fetchProfile("UNKNOWN").recoverWith({ //recoverWith with the args that we known for sure that it wont fail
    case e: Throwable => SocialNetwork.fetchProfile("fb.id.1-zuck")
  })

  //if first fail -> fallbackto -> if fail -> exception use the first one
  val fallbackResult = SocialNetwork.fetchProfile("UNKNOWN").fallbackTo(SocialNetwork.fetchProfile("fb.id.0-dummy"))

  // online banking app
  case class User(name: String)
  case class Transaction(sender: String, receiver: String, amoint: Double, status: String)
  object BankingApp {
    val name = "THE BANK"

    def fetchUser(name: String): Future[User] = Future {
      //simulate long computation
      Thread.sleep(500)
      User(name)
    }

    def createTransaction(user: User, merchantname: String, amount: Double): Future[Transaction] = Future {
      Thread.sleep(100)
      Transaction(user.name, merchantname, amount, "SUCCESS")
    }

    def purchase(username: String, item: String, merchantName: String, cost: Double): String = {
      //fetch user from DB
      //create a transaction
      //WAIT FOR THE TRANSACTION TO FINISH
      val transactionStatusFuture = for {
        user <- fetchUser(username)
        transaction <- createTransaction(user, merchantName, cost)
      } yield transaction.status

      //`Await` block until get transactionStatusFuture finish for at most 2 seconds (or wait for at most 2 seconds)
      Await.result(transactionStatusFuture, 2.seconds) //implicit conversions -> pimp my library
    }
  }

  //block until futures are completed (Await)
  println(BankingApp.purchase("Gun", "Iphone", "MERCHANT", 55555))

  // Promises
  // set future at some point(like a setter)
  //basically store whaat you want to manipulate in promises -> future create using that promises will do it
  val promise = Promise[Int]() // "controller" over a future
  val future = promise.future //undefined future

  // thread 1 - "consumer"
  future.onComplete {
    case Success(r) => println("[consumer] I've received " + r)
  }

  // thread 2 - "producer"
  val producer = new Thread(() => {
    println("[producer] crunching numbers...")
    Thread.sleep(500)
    // "fulfilling" the promise
    promise.success(42) //line 146 will match Success(42) (don't have to call consumer.start() first like thread
    println("[producer] done")
  })

  producer.start()
  Thread.sleep(1000)

  /*
    1. fulfill a future immediately with a value
    2. inSequence(fa, fb)
    3. first(fa, fb) => new future with the first value of the two futures
    4. last(fa, fb) => new future with the last value of the two futures
    5. retryUntil[T](action: () => Future[T], condition: T => Boolean): Future[T]
   */

  // 1 - fulfill immediately
  def fulfillImmediately[T](value: T): Future[T] = Future(value)
  // 2 - inSequnce()
  def inSequence[A, B](first: Future[A], second: Future[B]): Future[B] = {
    first.flatMap(_ => second)
  }
  //3 - first out of future
  def first[A](fa: Future[A], fb: Future[A]) = {
    val promise = Promise[A]

    fa.onComplete(promise.tryComplete)
    fb.onComplete(promise.tryComplete)

    promise.future //set the resulting future with the first finished promise
  }
  // 4 - last
  def last[A](fa: Future[A], fb: Future[A]) = {
    // 1 promise which both future will try to complete
    // 2 promise which the LAST future will complete
    val bothPromise = Promise[A]
    val lastPromise = Promise[A]
    val checkAndComplete = (result: Try[A]) =>
      if (!bothPromise.tryComplete(result))
        lastPromise.complete(result)
    fa.onComplete(checkAndComplete)
    fb.onComplete(checkAndComplete)

    lastPromise.future //set the resulting future with the first finished promise
  }

  val fast = Future {
    Thread.sleep(100)
    12
  }

  val slow = Future {
    Thread.sleep(20000)
    58
  }
  first(fast, slow).foreach(f => println(s"FIRST: $f"))
  last(fast, slow).foreach(l => println(s"LAST: $l"))

  //5 - retry until
  def retryUntil[T](action: () => Future[T], condition: T => Boolean): Future[T] = {
    action()
    .filter(condition)
    .recoverWith({
      case _ => retryUntil(action, condition)
    })
  }

  val random = new Random()
  val action = () => Future { //function of 0 param
    Thread.sleep(100)
    val nextValue = random.nextInt(100)
    println("generated" + nextValue)
    nextValue
  }

  retryUntil(action, (x:Int) => x < 50).foreach(result => println(s"settled at $result"))
  Thread.sleep(10000)

}
