## Options
- Wrapper for value that might not exist
    - avoid Null
- Use option to avoid Null
- Avoid null-related error
- use it with map flatmap(! return Options[...]) filter
    - orElse
    - fold,collect,toList
- If designed a method that return (some type) but may return null, return Option[that type] instead
    - if not null will return Some(that type val) -> use foreach to get inside some
        - can use Option.get bukt will crash if null
    - if null till return None
    - *Option.get* retrun val will crash if null
        -if Option(concrete val) use map(concrete val => ....) to get the concrete val
    - *Map.get(key)* return Option[val type](val) -> A OPTION use flatmap/map/filter/foreach next ...
- **for-comprehension saves lives!!!!**

## Some and Options
- Option is a wrapper for a value that might not exist. attempt to address the existance of null.
- Some is a subclass of Option and wraps a concrete value.
- None is also a subclass of Option and is a singleton for the absence of value.
- Option is used to wrap the result of something that might be null. Do not use Some to wrap suche a call.
```aidl
val badResult = Some(someNullMethod())
val goodResult = Option(someNullMethod())
```
- When using unsafe API, wrap with Option and eventually fallback with a orElse to chain a safe result:
```
val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))
When creating unsafe API, wrap the result with Some or return None instead of null.
```
## handling failures
- A better way to handle exception is to use a Try block that is subclassed by Failure and Success.
- We call a potentially unsafe method in the apply method of the Try construct:
```
val someResult = Try(unsafeMethod())
```
- When using unsafe API, wrap with Try and eventually fallback with a orElse to chain a safe result:
```
val fallbackTry = Try(unsafeMethod()) orElse Try(backupMethod())
When creating unsafe API, return a Try[type] wich is either a Success or Failure instance.
```
- map, flatMap, filter behaves like Options
    - filter can create Failure if condition is not met ...
- **for saves lives!!!**