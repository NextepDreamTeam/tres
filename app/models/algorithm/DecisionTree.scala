package models.algorithm

import models.commons.{Item, WidgetTag, Behavior}

import scala.collection.mutable.ListBuffer



class DecisionTree {
  class Node(val data: String, val children: ListBuffer[(String, Node)])

  private var root: Node = null

  def create(behaviorList: List[Behavior], wtagList: List[WidgetTag], itemList: List[Item]): Node = {
    val newBehaviorList = behaviorList
    val newWtagList = wtagList.sortWith((left, right) => Id3Impl.gain(behaviorList, itemList, left) > Id3Impl.gain(behaviorList, itemList, right))
    val w = newWtagList.head
    wtagList.filter(t => t.equals(w))

    root = new Node(w.name, ListBuffer[(String, Node)]())
    val cont = 0
    val item = newBehaviorList.head.item
    for(b <- newBehaviorList){
      if(b.item != item)
        cont += cont
    }
    if(cont==0)
      root.children = (item.id,   )

    val attributeValues = Id3Impl.getDistinctListOfActions(behaviorList, w.name)

    attributeValues.foreach {
      a => {
        val newBehaviorList = Id3Impl.getBehaviorsWithWTagAndAction(behaviorList, w.name, a)
        val child = create(newBehaviorList, wtagList, itemList)

        root.children += Tuple2[String, Node](a, child)
      }
    }
    root
  }
}
