package exercise.part3

import scala.util.Random

object Options extends App {

  val config: Map[String, String] = Map(
    "host" -> "192.168.1.1",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected"
  }

  object Connection {
    val random = new Random(System.nanoTime()) //simulate faulty connection

    def apply(host: String, port: String): Option[Connection] =
      if(random.nextBoolean()) Some(new Connection)
      else None
  }

  //try to establish connection
  //can connect -> print connect method

  //obtain a port and host (which might or might not be there
  val host = config.get("host") // option
  val port = config.get("port")

  /*
  basic logic
  if(h!= null)
    if(p != null)
      return Connection.apply(h,p)
  return null

   */

  val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h, p)))

  /*
  if(c != null)
    return c.connect
  return null

   */
  val connectionStatus = connection.map(c => c.connect) //if we have it will be Some(...).map(....)

  //if (connectionStatus != null) println(None) else print Some(connectionStatus.get)
  println(connectionStatus) //Some("connect")
  /*
    if(connectionStatus != null)
      print(status)
   */
  connectionStatus.foreach(println)

  //shorter using for
//  val k = config.get("host")
  ////    .flatMap(host => config.get("port")
  ////      .flatMap(port => Connection(host, port))
  ////      .flatMap(connection => Option(connection.connect))) //cant use flatMap cuz it need to be A => Options[B] but connection.connect is String
  ////      //so if we use we have to Option() it
  //////    .map(o => o.get)
  //////    .foreach(println(_) + "Last")
  ////  println(k.get + "Last") //get will cause NoSuchElementException: None.get

  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port))
      .map(connection => connection.connect))
    .foreach(x => println(x + "map"))

  //for-comprehension
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
  forConnectionStatus.foreach(println)

}
