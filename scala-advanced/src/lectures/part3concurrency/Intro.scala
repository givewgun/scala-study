package lectures.part3concurrency

import java.util.concurrent.Executors


object Intro extends App{

  //JVM threads
  /*
    interface Runnable {
      public void run()
    }
   */
  //thread instance
  val runnable = new Runnable {
    override def run(): Unit = println("Running in parallel")
  }
  val aThread = new Thread(runnable)

  aThread.start() //gives the signal to JVM to start a JVM thread (where actual code is run
  //runnable.run() //doesn't do anything in parallel cuz not a thread!!!
  //create a JVM thread on top OS thread
  aThread.join() // blocks until aThread finishes running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("hello"))) //run() lambda
  /*
    new Thread(() => {
      //runnable of something
    })
   */
  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("goodbye")))
  threadHello.start()
  threadGoodbye.start()
  // different runs produce different results!!!!

  // executors
  //reuse thread by using pool
  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in thread pool"))

  pool.execute(() => {
    Thread.sleep(1000)
    println("done after 1 sec")
  })
  pool.execute(() => {
    Thread.sleep(1000)
    println("almost done")
    Thread.sleep(1000)
    println("done after 2 sec")
  })

  //shutdown all threads
  //pool.shutdown() // throw the exeption in the calling thread, not receivving anymore actions but the remaining is still there
  //pool.shutdownNow() //shutdown immediately empty all mem
  //

  //problems
  def runInParallel = {
    var x = 0

    val thread1 = new Thread(() => {
      x = 1
    })

    val thread2 = new Thread(() => {
      x = 2
    })

    thread1.start()
    thread2.start()
    println(x) // 000000 ???? often it gets execute before the above (race condition)
  }

  for (_ <- 1 to 100) runInParallel
  //race conditions
  class BankAccount(@volatile var amount: Int) {
    override def toString: String = "" + amount
  }

  def buy(account: BankAccount, thing: String, price: Int): Unit = {
    account.amount -= price //account.amount = account.amount - price
//    println("Bought " + thing)
//    println("My account is now " + account)
  }

  for ( i <- 1 to 10000) {
    val account = new BankAccount(50000)
    val thread1 =  new Thread(() => buy(account, "Shoes", 3000))
    val thread2 =  new Thread(() => buy(account, "iPhones", 4000))

    thread1.start()
    thread2.start()
    Thread.sleep(100)
    if (account.amount != 43000) println("race: " + account.amount)
    //race  AHA 46000 2 threads race -> buy all but decrease only 4000
    /*
      thread1 (shoe): 50000
        - account = 50000 - 3000 = 47000
      thread2 (iphone): 50000
        - account = 50000 - 4000 = 46000 overwrites the momory of account.amount
     */

    //option#1: use synchronized
    def buySafe(account: BankAccount, thing: String, price: Int): Unit = {
      //no two threads can evaluate this at the same time
      account.synchronized({
        account.amount -= price
      })
    }
  }

  //option #2: use @volatile in front of the volatile val -> read and write on it is synchronized

  /*
    1. Construct 50 inceptions threads
      Thread1 -> Thread2 -> Thread3 ....
        println(hello from thread #3)
        in reverse order
   */
  def inceptionThreads(maxThread: Int, i: Int = 1): Thread = new Thread(() => {
    if (i < maxThread) {
      val newThread = inceptionThreads(maxThread, i + 1)
      newThread.start()
      newThread.join() //wait for it to finish
    }
    println(s"Hello from thread $i")
    //50 49 48 .....
  })


  /*
    2.
      1.what is the biggest value on x = 100
      2.what is the smallest value on x = 1 // read x = 0 at every thread  -> write x = 1 at the same time

   */
  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x + 1))
  threads.foreach(_.start())


  /*
  sleep fallacy
    - wrong practice of syncing threads by using sleep
   */
  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awesome"
  })

  message = "Scala sucks"
  awesomeThread.start()
  Thread.sleep(2000)
  awesomeThread.join() // guarantee that it finishes
  println(message)
  /*
    what is the value of message? = almost Scala is awesome
    is it guarantee? NO

    (main thread)
      message = "Scala Sucks"
      sleep() - relieves execution
    (awesome thread)
      sleep() - relieves execution
    (OS gives CPU to some important thread - takes CPU for more than 2 seconds)
    (OS gives COU back to main thread)
      println("Scala sucks")
    (OS gives COU back to awesome thread)
      message = "awesome" //TOO LATE
   */
  //how to fix this???
  //synchronizing doesn't work! becuase not concurrent, this is sequencial
  //use .join()

}
