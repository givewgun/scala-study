package lectures.part4Implicits

/*
  It's like a describe a collection or properties/method that a type must have in order to belong to that type group
 */
trait MyTypeClassTemplate[T] { //Type class template
  def action(value: T): String
}

object MyTypeClassTemplate { //use this companion object with [type] to access all type class interface (as apply is like () and auto injected)
  def apply[T](implicit instance: MyTypeClassTemplate[T]) = instance
}