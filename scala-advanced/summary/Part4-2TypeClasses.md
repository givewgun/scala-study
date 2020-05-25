# Part 4-2
## Type class
- defining like a traits
```
trait MyTypeClassTemplate[T] {
  def action(value: T): String
}
```
- type class instance (often implicit)
```
implicit object MyTypeClassInstance extends MyTypeClassTemplate[Int] {...}
```
- Invoking type class instance
    - use this companion object with [type] to access all type class interface (as apply is like () and auto injected)
```
object MyTypeClassTemplate {
  def apply[T](implicit instance: MyTypeClassTemplate[T]) = instance
}
```
```
MyTypeClassTemplate[Int].action(2)
```
## Type enrichment (implicit)
- pimp my library
- wrap it
```
implicit class ConversionClass[T](value: T) {
    def action(implicit instance: MyTypeClassTemplate[T]) = instance.action(value)
}
```
```
2.action
```
- it goes something like this
     - 2.action
        - is there .action in [Int] ? No
        - try wrapping User with something that has .action
        - wrap [Int] to ConversionClass
        - new ConversionClass[Int](2).action(instance of MyTypeClassTemplate with type [Int]) //find instance
            -find implicit MyTypeClassInstance object -> MyTypeClassInstance (extends MyTypeClassTemplate[Int])
    - new ConversionClass[Int](2).action(MyTypeClassInstance)
- Context Bound
    - bound the inject of implicit to specified type
    ```
    def htmlSugar[T: HTMLSerializer](content: T): String = { //bound the inject of implicit to HTMLSerializer type
        //use serializer by name
        val serializer = implicitly[HTMLSerializer[T]]
        s"<html><body> ${content.toHTML(serializer)}</body></html>"
        s"<html><body> ${content.toHTML}</body></html>" //can't specify by name cuz already injected
      }
    ```




