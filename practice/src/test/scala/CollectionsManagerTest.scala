
import collections.CollectionManager
import org.scalatest._

import scala.collection.mutable.{ListBuffer, Map}

class CollectionsManagerTest extends FlatSpec {

  "A collection manager" should "update list from map by multiplying and removing what is not in map" in {
    val lists = ListBuffer(1, 2, 3, 4, 5)
    val mulMap = Map((1, 3), (2, 4), (3, 5))

    val cm = new CollectionManager
    assert(cm.getUpdatedList(lists, mulMap) == ListBuffer(3, 8, 15))
  }

  it should "update list by filtering and mapping correctly" in {
    val lists = ListBuffer(1, 2, 3, 4, 5)
    val mulMap = Map((1, 3), (2, 4), (3, 5))

    val cm = new CollectionManager
    assert(cm.betterGetUpdatedList(lists, mulMap) == ListBuffer(3, 8, 15))
  }



}
