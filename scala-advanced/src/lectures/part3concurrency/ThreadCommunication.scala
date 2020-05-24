package lectures.part3concurrency

import scala.collection.mutable
import scala.util.Random

object ThreadCommunication extends App {

  /*
    the producer-consumer problem
    producer -> [ x ] -> consumer
    how can producer and consumer communicate and run in order when parallel?
   */
  class SimpleContainer {
    private var value: Int = 0

    def isEmpty: Boolean = value == 0
    def get = {
      val result = value
      value = 0
      result
    }
    def set(newValue: Int) = value = newValue
  }

  def naiveProdCons(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("Consumer waiting ....")
      while (container.isEmpty) {
        println("Consumer still waiting")
      }

      println("Consumer has consume " + container.get)
    })

    val producer = new Thread(() => {
      println("Producer computing ...")
      Thread.sleep(500)
      val value = 42
      println("Producer has produced the value " + value)
      container.set(value)
    })

    consumer.start()
    producer.start()

  }

    //wait and notify (allow in synchronized expression)
  def smartProdCons(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("Consumer waiting ....")
      container.synchronized({
        container.wait()
      })
      //container must have some value
      println("Consumer has consume " +  container.get)
    })

    val producer = new Thread(() => {
      println("Producer hard at work ...")
      Thread.sleep(2000)
      val value = 42
      container.synchronized({
        println("Producer has produced the value " + value)
        container.set(value)
        container.notify() //CONSUMER WAKE UPPPP
      })
    })
    consumer.start()
    producer.start()
  }

//  smartProdCons()

  /*
    producer -> [? ? ?] -> consumer //buffer
    must have two ways block (full buffer etc.)
   */
  def prodConsLargeBuffer(): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    val consumer = new Thread(() => {
      val random = new Random()

      while (true){
        buffer.synchronized({
          if (buffer.isEmpty) { //lock buffer
            println("Consumer buffer waiting")
            buffer.wait() //temporary unlock the AnyRefs instance (buffer)
          }
          //at least one val in buffer now
          val x = buffer.dequeue()
          println("Consumer consume " + x)

          // TODO
          buffer.notify() //In case producer is sleeping, lazy? WORKKKK

        })
        Thread.sleep(random.nextInt(500))
      }
    })

    val producer = new Thread(() => {
      val random = new Random()
      var i = 0

      while (true) {
        buffer.synchronized({
          if (buffer.size == capacity) {
            println("Producer buffer is full, waiting ....")
            buffer.wait()
          }
          //at least 1 empty space
          println("Producer produce " + i)
          buffer.enqueue(i)

          // TODO
          buffer.notify() //In case consumer is sleeping, are you lazy? GET YOUR NEW GOODS

          i += 1
        })
        Thread.sleep(random.nextInt(500))
      }
    })

    producer.start()
    consumer.start()
  }

//  prodConsLargeBuffer()
  /*
  Prods-Cons, level3 (multiple producers & consumers, same buffer
   */

  class Consumer(id: Int, buffer: mutable.Queue[Int]) extends Thread {
    override def run(): Unit = {
      val random = new Random()

      while (true){
        buffer.synchronized({
          /*
            producer produces value, two Cons are waiting
            nofity() = notifies ONE consumer, notifies on buffer
            notifies the other consumer -> break if buffer is empty
            while to like receive notify -> loop back to check isEmpty first not blindly consume
           */
          while (buffer.isEmpty) { //need to be both awake and buffer not empty (level two just or)
            println(s"Consumer:$id buffer waiting")
            buffer.wait() //temporary unlock the AnyRefs instance (buffer)
          }
          //at least one val in buffer now
          val x = buffer.dequeue()
          println(s"Consumer: $id consume " + x)

          buffer.notify() //In case producer is sleeping, lazy? Somebody WORKKKK

        })
        Thread.sleep(random.nextInt(500))
      }
    }
  }

  class Producer(id: Int, buffer: mutable.Queue[Int], capacity: Int) extends Thread {
    override def run(): Unit = {
      val random = new Random()
      var i = 0

      while (true) {
        buffer.synchronized({
          while (buffer.size == capacity) {
            println(s"Producer $id buffer is full, waiting ....")
            buffer.wait()
          }
          //at least 1 empty space and I am awake
          println(s"Producer $id produce " + i)
          buffer.enqueue(i)

          // TODO
          buffer.notify() //In case consumer is sleeping, are you lazy? Somebody get the GOODDSSSS

          i += 1
        })
        Thread.sleep(random.nextInt(500))
      }
    }
  }

  def multiProdCons(nConsumers: Int, nProducers: Int): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    (1 to nConsumers).foreach(id => new Consumer(id, buffer).start())
    (1 to nProducers).foreach(id => new Producer(id, buffer, capacity).start())
  }

  multiProdCons(3, 3)

  /*
    Exercises
    1. Think of an example where notifyAll acts in a diffrent way than notify
    2. create deadlock
    3. create live lock
   */

  //1 Notifyall
  def testNotifyAll() = {
    val bell = new Object

    (1 to 10).foreach(i => new Thread(() => {
      bell.synchronized({
        println(s"[thread $i] waiting....")
        bell.wait()
        println(s"[thread $i] awake!")
      })
    }).start())

    new Thread(() => {
      Thread.sleep(2000)
      println("[announcer] rock and roll")
      bell.synchronized({
        bell.notifyAll() // every thread sleep and wake at the same time
        //bell.notify() only one thread wakes up
      })
    })
  }

  //2. Deadlock
  case class Friend(name: String) {

    def bow(other: Friend) = {
      this.synchronized({
        println(s"$this I'm bowing to $other")
        other.rise(this)
        println(s"this: my friend $other has risen")
      })
    }

    def rise(friend: Friend) = {
      this.synchronized({
        println(s"$this I am rising to my friends $friend")
      })
    }

    var side = "right"
    def switchSide() = {
      if (side == "right") side = "left"
      else side = "right"
    }

    def pass(other: Friend) = {
      while (this.side == other.side) {
        println(s"$this: OH but please $other, feel free to pass")
        switchSide()
        Thread.sleep(2000)
      }
    }

  }
  val sam = Friend("sam")
  val john = Friend("john")

  new Thread(() => sam.bow(john)).start() //sam's lock, |  then john is lock
  new Thread(() => john.bow(sam)).start() //john's lock |  then sam's lock

  //3 - livelock (no threads are actually block but no threads are free to execute cuz keep giving to each other)
  new Thread(() => sam.pass(john)).start()
  new Thread(() => john.pass(sam)).start()

}
