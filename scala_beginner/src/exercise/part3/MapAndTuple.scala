package exercise.part3

import scala.annotation.tailrec

object MapAndTuple extends App {

  //Duplicate key -> data loss
  val phoneBook = Map(("Jim", 5555), "Gun" -> 7878, "Jim" -> 8888).withDefaultValue(-1)
  println(phoneBook)

  //social network

  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    network + (person -> Set())
  }

  def friend(network: Map[String, Set[String]], personA: String, personB: String): Map[String, Set[String]] = {
    val friendsA = network(personA)
    val friendsB = network(personB)

    network + (personA -> (friendsA + personB)) + (personB -> (friendsB + personA)) //replace oldparinng with new pairing
  }

  def unFriend(network: Map[String, Set[String]], personA: String, personB: String): Map[String, Set[String]] = {
    val friendsA = network(personA)
    val friendsB = network(personB)

    network + (personA -> (friendsA - personB)) + (personB -> (friendsB - personA))
  }

  def remove(network: Map[String, Set[String]], person: String):  Map[String, Set[String]] ={
    //remove the friendship of that person first before removing indivudual (tangling friendship)
    @tailrec
    def removeHelper(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] = {
      if(friends.isEmpty) networkAcc
      else removeHelper(friends.tail, unFriend(networkAcc, person, friends.head))
    }

    val unfriended = removeHelper(network(person), network)
    unfriended - person

  }

  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Bob"), "Mary")
  println(network)
  println(friend(network, "Bob", "Mary"))
  println(unFriend(friend(network, "Bob", "Mary"), "Mary", "Bob"))
  println(remove(friend(network, "Bob", "Mary"), "Bob"))

  //Jim, Bob, Mary
  val people = add(add(add(empty,"Bob"), "Jim"), "Mary")
  val jimbob = friend(people, "Jim", "Bob")
  val testnet = friend(jimbob, "Bob", "Mary")

  //find num friends
  def nFriends(network: Map[String, Set[String]], person: String): Int ={
    if(!network.contains(person)) 0
    else network(person).size
  }

  //most friend
  def nFriends(network: Map[String, Set[String]]): String ={
    network.maxBy(pair => pair._2.size)._1 //use pair._2.size as a criteria
  }

  //no friends
  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int ={
    network.count(pair => pair._2.isEmpty) // filterKeys(k => network(k).isEmpty)
  }

  def socialConnection(network: Map[String, Set[String]], personA: String, personB: String): Boolean = {
    //BFS
    @tailrec
    def bfs(target: String, consideredPeople: Set[String], remainingPeople: Set[String]): Boolean = {
      if(remainingPeople.isEmpty) false //cant find
      else{
        val person = remainingPeople.head
        if(person == target) true
        else if(consideredPeople.contains(person)) bfs(target, consideredPeople, remainingPeople.tail)
        else bfs(target, consideredPeople + person, remainingPeople.tail ++ network(person))
      }
    }

    bfs(personB, Set(), network(personA) + personA)
  }

  println(socialConnection(testnet, "Mary", "Jim"))

}
