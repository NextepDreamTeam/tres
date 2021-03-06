package models.algorithm

import models.commons.{WidgetTag, Interaction, Behavior, Item}

import scala.collection.mutable.ListBuffer



/**
  * Trait used to define signature of the supported methods by Id3 models.algorithm
  */
trait Id3{
  def entropy(behaviorList : List[Behavior], itemList: List[Item]): Double
  def gain(behaviorList: List[Behavior], itemList: List[Item], attributeTag: WidgetTag): Double
}

/**
  * Object that implements the methods of the trait
  *
  */
object Id3Impl extends Id3 {

  /**
    * Method that returns only the distinct action in a list of actions filtered by widgetTag
    * @param behaviors : List[Behavior]
    * @param wtag : String
    * @return List[String]
    */
  def getDistinctListOfActions(behaviors: List[Behavior], wtag: String): List[String] = {
    behaviors.flatMap(b => b.interactions).filter(p => p.widgetTag.name == wtag).map(a => a.action).distinct
  }


  /**
    * Method that return only the behaviours that contains the interaction with widgetTag wtag and action waction
    * @param behaviors : List[Behaviours]
    * @param wtag : String
    * @param waction : String
    * @return List[Behaviors]
    */
  def getBehaviorsWithWTagAndAction(behaviors: List[Behavior], wtag: String, waction: String): List[Behavior] = {
    behaviors.filter(b => b.interactions.contains(Interaction(WidgetTag(wtag), waction, None)))
  }

  /**
    * Method that calculates entropy
    * @param behaviorList : List[Behavior]
    * @param itemList : List[Item]
    * @return Double
    */
  def entropy(behaviorList: List[Behavior], itemList: List[Item]): Double = {
    var entropy: Double = 0
    var numberTargetOccurences = new ListBuffer[Int]()
    for (i <- itemList) {
      var sum: Int = 0
      for (b <- behaviorList) {
        if (i.equals(b.item))
          sum = sum + 1
      }
      numberTargetOccurences += sum
    }
    for (n <- numberTargetOccurences) {
      if (n != 0)
        entropy = entropy - ((n / behaviorList.length.toDouble) * (Math.log(n / behaviorList.length.toDouble) / Math.log(2)))
    }
    entropy
  }

  /**
    * Method that calculates gain
    * @param behaviorList : List[Behavior]
    * @param itemList : List[Item]
    * @param attributeTag : WidgetTag
    * @return Double
    */
  def gain(behaviorList: List[Behavior], itemList: List[Item], attributeTag: WidgetTag): Double = {
    var gain = entropy(behaviorList, itemList) // data set entropy ( entropy(S) )
    for (i <- getDistinctListOfActions(behaviorList, attributeTag.name)) {
      gain = gain - ((getBehaviorsWithWTagAndAction(behaviorList, attributeTag.name, i).length / behaviorList.length.toDouble)
        * entropy(getBehaviorsWithWTagAndAction(behaviorList, attributeTag.name, i), itemList))
    }
    gain
  }

}
