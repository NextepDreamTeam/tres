package models.algorithm

import models.commons._

import scala.collection.immutable.ListMap


sealed trait Tree{
  def size: Int
}
case class Node(behaviorList: List[Behavior], widgetTag: WidgetTag, children: ListMap[String,Tree]) extends Tree {
  override def size: Int = 1 + children.map(c => c._2.size).sum
  override def toString: String = s"Tree:  ${widgetTag.name} \n " + children
}
case class Leaf(behaviorList: List[Behavior]) extends Tree {
  override def size: Int = 1
  override def toString: String = "\nLeaf " + behaviorList.size + " " + DecisionTree.getDistinctItemList(behaviorList)
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
    if (behaviorList.isEmpty) throw new Exception("Behaviors list is empty!")

    //control if widget tags intersection, if no return a leaf
    val wtagList: List[WidgetTag] = getDistinctWidgetTagList(behaviorList)
    val unusedWidgetTagList = wtagList.filter(t => !wtagsUsed.contains(t))
    val itemList = getDistinctItemList(behaviorList)
    unusedWidgetTagList.isEmpty || behaviorList.size==1 || itemList.size == 1 match{
      case true => Leaf(behaviorList)
      case false =>{
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
                (action, getBehaviorsWithInteraction(behaviorList,interaction))
              }
            ).filter(a => a._2.nonEmpty).map(t => (t._1,create(t._2,widgetTag::wtagsUsed))):_*)
            Node(behaviorList, widgetTag, branches)
          }
        }
      }
    }

  }
}
