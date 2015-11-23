package models.algorithm

import models.commons._


sealed trait Tree
case class Node(behaviorList: List[Behavior], childrens: List[Tree]) extends Tree
case class Leaf() extends Tree


object DecisionTree {

  def getDistinctWidgetTagList(behaviorList: List[Behavior]): List[WidgetTag] = {
    behaviorList.flatMap(b => b.interactions.map(i => i.widgetTag)).distinct
  }

  /*def create(behaviorList: List[Behavior], wtagsUsed: List[WidgetTag] = Nil): Tree = {
    //ritornare leaf se i padri classificano tutti i wtag

    /*val newWtagList = wtagList.sortWith((left, right) =>
      Id3Impl.gain(behaviorList, itemList, left) > Id3Impl.gain(behaviorList, itemList, right)
    )
    val wTag = newWtagList.head
    wtagList.filter(t => t.equals(wTag))*/


    //Node(behaviorList, wTag, create(), create())
  }*/
}
