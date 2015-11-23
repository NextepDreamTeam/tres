package models.algorithm

import models.commons.{Item, WidgetTag, Behavior}
import models.storage._

/**
  * Created by aandelie on 14/11/15.
  */
object Id3Service extends AlgorithmService {
  var behaviorDao: BehaviorDao = BehaviorOdb
  var widgetTagDao: WidgetTagDao = WidgetTagOdb
  var itemDao: ItemDao = ItemOdb

  private var decisionTree: Option[Tree] = None

  /**
    * Method that returns true if the models.algorithm is ready to make the recommendation
    * @return Boolean
    */
  override def ready(): Boolean = {
    decisionTree match {
      case None => false
      case Some(x) => true
    }
  }

  override def start(): Unit = {
    val behaviors: List[Behavior] = behaviorDao.all()
    val able: Boolean = behaviors.size >= 100
    able match {
      case true => decisionTree = Option(DecisionTree.create(behaviors))
      case false =>
    }
  }
}
