#Part3-2

## Future
- `Future[T]` is a computation which will finish *at some point*
```
import ExecutionContext.Implicits.global

val recipesFuture: Future[List[Recipe]] = Future {
    //some code that takes a long time to run
    jamieOliverDb.getAll("Chicken")
} //ec is passed implicitly
```
- non-blocking processing
    - Registering a foreach callback has the same semantics as onComplete,
    - with the difference that the closure is only called if the future is completed successfully.
```
val f: Future[List[String]] = Future {
  session.getRecentPosts
}

f onComplete {
  case Success(posts) => for (post <- posts) println(post)
  case Failure(t) => println("An error has occurred: " + t.getMessage)
}

f foreach { posts =>
  for (post <- posts) println(post)
}
```
- map, flatMap, filter, for comprehension
```
  val nameOnTheWall = mark.map(profile => profile.name) //Future[Profile] => Future[String]
  val markBestFriend = mark.flatMap(profile => SocialNetwork.fetchBestFriend(profile)) //Future[Profile] => Future[anotherProfile]
  val zuckBestFriendRestricted = markBestFriend.filter(profile => profile.name.startsWith("Z"))

  //for comprehension
  for {
    mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
    bill <- SocialNetwork.fetchBestFriend(mark)
  } mark.poke(bill)
```

- fallbacks
```
future.recover { case NotFoundException => ....}
```
- clocking if need be (Await)
```
val txStatus = Await.result(transaction, 1 seconds) //at most 1 second
```

## Promises
- Futures are immutable, "read-only objects"
- *Promises* are "writable-once" container over a future
### example
- thread 1:
    - create an empty promise
    - know how to handle result
    ```
    val p = Promis[Int]()
    val future = p.future // promise wraps future, future now is undefined

    future.onComplete {
        case Success(value) => ...
        case Failure(ex) => ...
    }
    ```
- thread 2
    - hold a promise
    - fulfill or fails the promise
    - all of the belows will trigger completion of a future in thread1 (.onComplete....)
    ```
    val result = doComputation()
    p.success(result)
    ```
    **OR**
    ```
    p.failure(new RunnableException(...))
    ```
    **OR**
    ```
    p.complete(Try {....})
    ```
- https://docs.scala-lang.org/overviews/core/futures.html
- https://stackoverflow.com/questions/13381134/what-are-the-use-cases-of-scala-concurrent-promise
- https://stackoverflow.com/questions/13381134/what-are-the-use-cases-of-scala-concurrent-promise