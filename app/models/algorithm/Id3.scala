package models.algorithm

import models.commons.{WidgetTag, Interaction, Behavior, Item}

import scala.collection.mutable.ListBuffer



/**
  * Trait used to define signature of the supported methods by Id3 models.algorithm
  */
trait Id3{
  def entropy(behaviorList : List[Behavior], itemList: List[Item]): Double
  def gain(behaviorList: List[Behavior], itemList: List[Item], attributeTag: WidgetTag): Double
  def start(behaviorList: List[Behavior], wtagList: List[WidgetTag], itemList: List[Item])

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
    * Method that return anly the behaviours that contains the interaction with widgetTag wtag and action waction
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
        if (i.id.eq(b.item.id))
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

  /*
  class Node(val data: String, val children: ListBuffer[(String, Node)])

  class DecisionTree {


    private var root: Node = null

    /*
    def preorder(visit: WidgetTag => Unit) {
      def recur(n: Node) {
        visit(n.data)
        for (c <- n.children) recur(c)
      }
      recur(root)
    }

    def postorder(visit: WidgetTag => Unit) {
      def recur(n: Node) {
        for (c <- n.children) recur(c)
        visit(n.data)
      }
      recur(root)
    }

    def height(n: Node): Int = {
      1 + n.children.foldleft(-1)((h, c) => h max height(c))
    }

    def size(n: Node): Int = {
      1 + n.children.foldLeft(0)((s, c) => s + size(c))
    }
    */

    def create(behaviorList: List[Behavior], wtagList: List[WidgetTag], itemList: List[Item]): Node = {

      /*
         Step 1: gain su tutti gli attributi
         Step 2: mi salvo attributo(Nodo) dentro una variabile e i figli come tutti i possibili valori dell'attributo
         Step 3: chiamata ricorsiva con gain e lista di behavior per ogni valore del padre(attributo)
         */
      wtagList.sortWith((left, right) => gain(behaviorList, itemList, left) > gain(behaviorList, itemList, right))
      val w = wtagList.head
      wtagList.filter(t => t.equals(w))

      root = new Node(w.name, ListBuffer[(String, Node)]())

      val attributeValues = getDistinctListOfActions(behaviorList, w.name)

      attributeValues.foreach {
        a => {
          val newBehaviorList = getBehaviorsWithWTagAndAction(behaviorList, w.name, a)
          val child = create(newBehaviorList, wtagList, itemList)

          root.children += Tuple2[String, Node](a, child)

        }
      }
      root
    }
  }
  */


  def start(behaviorList: List[Behavior], wtagList: List[WidgetTag], itemList: List[Item]) = ???

}
