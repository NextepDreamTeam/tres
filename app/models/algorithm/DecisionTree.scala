package models.algorithm

import models.commons._

import scala.collection.immutable.ListMap


sealed trait Tree
case class Node(behaviorList: List[Behavior], widgetTag: WidgetTag, children: ListMap[String,Tree]) extends Tree
case class Leaf(behaviorList: List[Behavior])

object DecisionTree {

  def getDistinctWidgetTagList(behaviorList: List[Behavior]): List[WidgetTag] = {
    behaviorList.flatMap(b => b.interactions.map(i => i.widgetTag)).distinct
  }

  def getDistinctItemList(behaviorList: List[Behavior]): List[Item] = {
    behaviorList.map(b => b.item).distinct
  }

  def create(behaviorList: List[Behavior], wtagsUsed: List[WidgetTag] = Nil): Tree = {
    val wtagList: List[WidgetTag] = getDistinctWidgetTagList(behaviorList)
    val unusedWidgetTagList = wtagList.filter(t => !wtagsUsed.contains(t))
    if (unusedWidgetTagList.isEmpty) new Leaf(behaviorList)
    val itemList = getDistinctItemList(behaviorList)
    val widgetTag = unusedWidgetTagList.sortWith((left, right) =>
        Id3Impl.gain(behaviorList, itemList, left) > Id3Impl.gain(behaviorList, itemList, right)
    ).head

    val actionList: List[String] = behaviorList.flatMap(behavior => behavior.interactions.map(i=>i.action)).distinct

    val branches: ListMap[String,Tree] = ListMap(actionList.map(
      action => {
        val interaction = Interaction(widgetTag, action)
        (action, create(behaviorList.filter(b => b.interactions.contains(interaction))) )
      }
    ):_*)
    Node(behaviorList, widgetTag, branches)
  }
}
