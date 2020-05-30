package collections

import scala.collection.mutable._
class CollectionManager {

  def getUpdatedList(numList: ListBuffer[Int], numMap: Map[Int, Int]): ListBuffer[Int] = {
    var trackNotFound = new ArrayBuffer[Int]
    var itemsRemovedCount = 0
    for (i <- numList.indices) {
      val num = numList(i)
      val optionNum = numMap.get(num)
      if (optionNum.isDefined) numList.update(i, optionNum.get * num)
      else trackNotFound += i
    }
    for (indexToDrop <- trackNotFound) {
      numList.remove(indexToDrop - itemsRemovedCount)
      itemsRemovedCount += 1
    }
    numList
  }

  def betterGetUpdatedList(numList: ListBuffer[Int], numMap: Map[Int, Int]): ListBuffer[Int] = {
    numList.filter(numMap.contains(_)).map((x: Int) => x * numMap(x))
  }

}
