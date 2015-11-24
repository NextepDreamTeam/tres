package models.algorithm

import models.commons._

import scala.collection.immutable.ListMap


sealed trait Tree{
  def size: Int
}
case class Node(behaviorList: List[Behavior], widgetTag: WidgetTag, children: ListMap[String,Tree]) extends Tree {
  override def size: Int = 1 + children.map(c => c._2.size).sum
  override def toString: String = s"Tree:  ${widgetTag.name} " + children.map(c => " \n \t --->" + c._2)
}
case class Leaf(behaviorList: List[Behavior]) extends Tree {
  override def size: Int = 1
  override def toString: String = "Leaf "
}

object DecisionTree {

  def getDistinctWidgetTagList(behaviorList: List[Behavior]): List[WidgetTag] = {
    behaviorList.flatMap(b => b.interactions.map(i => i.widgetTag)).distinct
  }

  def getDistinctItemList(behaviorList: List[Behavior]): List[Item] = {
    behaviorList.map(b => b.item).distinct
  }

  def getBehaviorsWithInteraction(behaviorList: List[Behavior],interaction: Interaction): List[Behavior] ={
    behaviorList.filter(b => b.interactions.contains(interaction))
  }

  def create(behaviorList: List[Behavior], wtagsUsed: List[WidgetTag] = Nil): Tree = {
    //control if widget tags intersection, if no return a leaf
    val wtagList: List[WidgetTag] = getDistinctWidgetTagList(behaviorList)
    val unusedWidgetTagList = wtagList.filter(t => !wtagsUsed.contains(t))
    unusedWidgetTagList.isEmpty || behaviorList.size==1 match{
      case true => Leaf(behaviorList)
      case false =>{
        val itemList = getDistinctItemList(behaviorList)
        val widgetTags = unusedWidgetTagList.sortWith((left, right) =>
          Id3Impl.gain(behaviorList, itemList, left) > Id3Impl.gain(behaviorList, itemList, right)
        )
        widgetTags match {
          case Nil => Leaf(behaviorList)
          case widgetTag :: xs =>{
            val actionList: List[String] = behaviorList.flatMap(behavior => behavior.interactions.map(i=>i.action)).distinct

            val branches: ListMap[String,Tree] = ListMap(actionList.map(
              action => {
                val interaction = Interaction(widgetTag, action)
                (action, create(getBehaviorsWithInteraction(behaviorList,interaction),widgetTag::wtagsUsed) )
              }
            ):_*)
            Node(behaviorList, widgetTag, branches)
          }
        }
      }
    }

  }
}
