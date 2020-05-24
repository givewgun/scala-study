# Part 3

## JVM Thread
- thread instance
```
  val runnable = new Runnable {
    override def run(): Unit = println("Running in parallel")
  }
  val aThread = new Thread(runnable)

  aThread.start() //gives the signal to JVM to start a JVM thread (where actual code is run
  //runnable.run() //doesn't do anything in parallel cuz not a thread!!!
  //create a JVM thread on top OS thread
  aThread.join() // blocks until aThread finishes running

```
- or lambda conversion
```
val aThread = new Thread(() => {println("YYYYYY"})
```
- `Thread.sleep(...)` to sleep the thread
    - if write in main not in new Thread will sleep the main Thread
-Executor and thread pool
```
//reuse thread by using pool
  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in thread pool"))
```
    - `pool.shutdown()` // throw the exeption in the calling thread, not receiving anymore actions but the remaining is still there
    - `pool.shutdownNow()` //shutdown immediately empty all memory
- Concurrency Problem
    - race conditions (concurrent)
        - use synchronized
        ```
        def buySafe(account: BankAccount, thing: String, price: Int): Unit = {
              //no two threads can evaluate this at the same time
              account.synchronized({
                account.amount -= price
              })
            }
        ```
        - or @volatile
    - sleep fallacy (sequential)
        - use `.join()`
    -dead lock
        - blocking
    ```
    new Thread(() => sam.bow(john)).start() //sam's lock, |  then john is lock
    new Thread(() => john.bow(sam)).start() //john's lock |  then sam's lock
    ```
    -live lock
        - no threads are actually block but no threads are free to execute cuz keep giving to each other
    ```
    new Thread(() => sam.pass(john)).start()
    new Thread(() => john.pass(sam)).start()
    ```
- `wait()` `notify()` `notifyAll()`
    -  `wait()` is to temporary release the lock on the object (that we use synchronized)
        - will wake again if receive notify from other thread
    - `notify()` is to notify one thread that is waiting to continue execution and lock the sync object
    - `notifyAll()` is to notify all threads to wake up
    - *view in ThreadCommunication.scala in lecture package for more details*
- Producer and Consumer !
    - use ideas of wait notify
        - multiples prods cons need to use while loop to check both if awake and conditions! (else will skip checking when awake)
    - *view in ThreadCommunication.scala in lecture package for more details*