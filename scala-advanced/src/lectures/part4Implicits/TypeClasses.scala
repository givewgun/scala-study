package lectures.part4Implicits

import java.sql.Date


object TypeClasses extends App {

  //server side rendering
  trait HTMLWritable {
    def toHTML: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHTML: String = s"<div>$name $age <a href=$email/> </div>"
  }

  User("John", 22, "kuy@gmail.com").toHTML
  /*
    1.  only works for the type we write
    2   only ONE implementation out of quite a number
   */

  //Option 2 - pattern matching
  object HTMLSerializerPM {
    def serializedtoHtml(value: Any)  = value match {
      case User(n, a, e) =>
//      case java.util.Date =>
      case _ =>
    }
  }
  /*
    1. lost type safety
    2. need too modify code everytime
    3. still ONE implementation
   */

  //Option 3
  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  implicit object UserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div>${user.name} ${user.age} <a href=${user.email}/> </div>"
  }

  val john = User("John", 22, "kuy@gmail.com")
  println(UserSerializer.serialize(john))

  //1. we can define serializer for other types
  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(date: Date): String = s"<div>${date.toString} </div>"
  }

  //2 we can define MULTIPLE serializer
  object PartialUserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div>${user.name} </div>"
  }


  // PART 2
  object HTMLSerializer { //companion?
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)

    def apply[T](implicit serializer: HTMLSerializer[T]) = serializer
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div style: color=blue>$value</div>"
  }

  println(HTMLSerializer.serialize(22)) // HTMLSerializer.serialize(22)(IntSerializer))
  println(HTMLSerializer.serialize(john))

  //access to entire type class interface (apply) using companion object
  println(HTMLSerializer[User].serialize(john)) //HTMLSerializer[User].apply(UserSerializer).serialize(john)

  // part 3
  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

  println(john.toHTML) //println(new HTMLEnrichment[User](john).toHTML(UserSerializer)) //original toHTML has no params -> find others
  /*
    - 1 extends to new types
    - 2 different implementation for the same type
    - 3 super expressive
   */

  //1
  println(2.toHTML)
  //2
  println(john.toHTML(PartialUserSerializer))

  /*
    - type class itself : HTMLSerializer[T] {....}
    - type class instances (some of which are implicits) : UserSerializer, IntSerializer
    - conversion with implicit class : HTMLEnrichment
   */

  // context bounds
  def htmlBoilerPlate[T](content: T)(implicit serializer: HTMLSerializer[T]): String = {
    s"<html><body> ${content.toHTML(serializer)}</body></html>"
  }

  def htmlSugar[T: HTMLSerializer](content: T): String = { //bound the inject of implicit to HTMLSerializer type
    //use serializer by name
    val serializer = implicitly[HTMLSerializer[T]]
    s"<html><body> ${content.toHTML(serializer)}</body></html>"
    s"<html><body> ${content.toHTML}</body></html>" //can't specify by name cuz already injected
  }

  //implicitly
  case class Permission(mask: String)
  implicit val defaultPermission = Permission("0744")

  //in some other part of the code
  val standardPerms = implicitly[Permission] //surface out implicit val of type Permission (check if an implicit value of type T is available and return it)


}
