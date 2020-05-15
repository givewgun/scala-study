package exercise.part3

import scala.util.{Random, Try}

object HandlingFailure extends App {

  val hostName = "localhost"
  val port = "8080"
  def renderHTML(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())

      if(random.nextBoolean()) "<html>....</html>"
      else throw new RuntimeException("Connection interrupt")
    }

    //wrap the unsafe(with throwing exception)with Try class
    def getSafe(url: String): Try[String] = Try(get(url))

  }

  object HttpService {
    val random = new Random(System.nanoTime())
    def getConnection(host: String, port: String): Connection =
      if(random.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port")

    def getSafe(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }

  val possibleConnection = HttpService.getSafe(hostName, port) //Success Failure
  val possibleHTML = possibleConnection.flatMap(conn => conn.getSafe("/home"))
  possibleHTML.foreach(renderHTML)

  //shorhand
  HttpService.getSafe(hostName, port)
    .flatMap(conn => conn.getSafe("/home"))
    .foreach(println)

  //for comprehension
  for{
    connection <- HttpService.getSafe(hostName, port)
    html <- connection.getSafe("/home")
  } yield renderHTML(html)





}
