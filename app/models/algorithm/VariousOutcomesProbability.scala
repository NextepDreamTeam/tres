package models.algorithm

import models.commons.{Behavior, Item, WidgetTag}

import scala.collection.mutable.ListBuffer

/**
  * Trait used to define signature of the supported methods by VariousOutcomesProbability in algorithm package
  */
trait VariousOutcomesProbability {

  def probabilityForItemWithWidgetTagAndAction(behaviorList: List[Behavior], itemList: List[Item], wtag: WidgetTag, action: String): List[(Item, String)]

  def probabilityForItemsInBehaviors(behaviorList: List[Behavior], itemList: List[Item]): List[(Item, String)]

}

/**
  * Object that implements the trait
  */
object VariousOutcomesProbabilityImpl {

  /**
    * Method that calculates the percentage and return the Item with the calculated percentage
    * @param behaviorList : List[Behavior]
    * @param itemList : List[Item]
    * @return ListBuffer[(Item, String)]
    */
  def findItemsAndCalculatePercentage(behaviorList: List[Behavior], itemList: List[Item]): ListBuffer[(Item, Double)] = {
    val totalBehaviors = behaviorList.length
    var output = new ListBuffer[(Item, Double)]()
    for (i <- itemList) {
      var sum: Int = 0
      for (b <- behaviorList) {
        if (i.id.eq(b.item.id))
          sum += 1
      }
      output += Tuple2(i, ((sum/totalBehaviors.toDouble)*100).toInt)
    }
    output.distinct
  }


  /**
    * Method that return the probability that an item could be chosen given the widgetTag and it's action
    * @param behaviorList : List[Behavior]
    * @param itemList : List[Item]
    * @param wtag : WidgetTag
    * @param action : String
    * @return List[(Item, String)]
    */
  def probabilityForItemWithWidgetTagAndAction(behaviorList: List[Behavior], itemList: List[Item], wtag: WidgetTag, action: String): List[(Item, Double)] = {
    val behaviors = Id3Impl.getBehaviorsWithWTagAndAction(behaviorList,wtag.name, action)
    findItemsAndCalculatePercentage(behaviors, itemList).toList
  }


  /**
    * Method that returns the probability that an item could be choosen from the entire list of behaviors
    * @param behaviorList : List[Behavior]
    * @param itemList : List[Item]
    * @return List[(Item, String)]
    */
  def probabilityForItemsInBehaviors(behaviorList: List[Behavior], itemList: List[Item]): List[(Item, Double)] = {
    findItemsAndCalculatePercentage(behaviorList, itemList).toList
  }
}
