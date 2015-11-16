package algorithm

import models.commons.Item
import models.commons.Behavior
import scala.collection.mutable.ListBuffer


/**
  * Trait used to define signature of the supported methods by Id3 algorithm
  */
trait Id3{
  def entropy(behaviorList : List[Behavior]): Double
  //def gain()
}

/**
  * Object that implements the methods of the trait
  * @extends Id3
  */
object Id3Impl extends Id3 {

  /**
    * Method that counts the occurences for each target
    * @param itemList
    * @param behaviorList
    * @return List[(Item, Int)] returns for every Item the number of occurences
    */
  def occurencesForEachTarget(itemList : List[Item], behaviorList: List[Behavior]): List[(Item, Int)] = {
    var numberTargetOccurences = new ListBuffer[Int]()
    for (i <- itemList){
      var sum: Int = 0
      for (b <- behaviorList){
        if (i == b.item)
          sum = sum+1
      }
      numberTargetOccurences += sum
    }
    var output = (itemList zip numberTargetOccurences.toList)
    output
  }

  def entropy(behaviorList : List[Behavior]): Double = ???

  def getActions(actions: List[String]): List[String] = actions.distinct

  /**
    *
    * @param behaviors
    * @return
    */
  def start(behaviors: List[Behavior]) = {
    val actions = behaviors.flatMap(b => b.interactions).filter(p => p.widgetTag.name.equals("categoria")).map(i => i.action)
    val unique = getActions(actions)
  }

}
