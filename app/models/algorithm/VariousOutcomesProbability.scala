package models.algorithm

import models.commons.{WidgetTag, Item, Behavior}

import scala.collection.mutable.ListBuffer


trait VariousOutcomesProbability {

  def probabilityForItemWithWidgetTagAndAction(behaviorList: List[Behavior], itemList: List[Item], wtag: WidgetTag, action: String): List[(Item, String)]

  def probabilityForItemsInBehaviors(behaviorList: List[Behavior], itemList: List[Item]): List[(Item, String)]

}

object VariousOutcomesProbabilityImpl {

  def findItemsWithPercentage(behaviorList: List[Behavior], itemList: List[Item]): ListBuffer[(Item, String)] = {
    val totalBehaviors = behaviorList.length
    var output = new ListBuffer[(Item, String)]()
    for (i <- itemList) {
      var sum: Int = 0
      for (b <- behaviorList) {
        if (i.id.eq(b.item.id))
          sum += 1
      }
      output += Tuple2(i, ((sum/totalBehaviors.toDouble)*100).toInt.toString+"%")
    }
    output
  }

  def probabilityForItemWithWidgetTagAndAction(behaviorList: List[Behavior], itemList: List[Item], wtag: WidgetTag, action: String): List[(Item, String)] = {
    val behaviors = Id3Impl.getBehaviorsWithWTagAndAction(behaviorList,wtag.name, action)
    findItemsWithPercentage(behaviors, itemList).toList
  }

  def probabilityForItemsInBehaviors(behaviorList: List[Behavior], itemList: List[Item]): List[(Item, String)] = {
    findItemsWithPercentage(behaviorList, itemList).toList
  }
}
