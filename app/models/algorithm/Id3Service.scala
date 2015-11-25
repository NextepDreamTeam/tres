package models.algorithm

import models.commons.{Interaction, Item, WidgetTag, Behavior}
import models.storage._
import play.api.Logger

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
      case true => {
        Logger.info("ID3 started")
        decisionTree = Option(DecisionTree.create(behaviors))
        Logger.info("ID3 complete")
      }
      case false => Logger.warn("ID3 isn't already yet")
    }
  }

  /**
    *
    * @param interactions
    * @return
    */
  override def getRecommendation(interactions: List[Interaction]): List[Item] = {
    interactions.map(i=> i.widgetTag).distinct.size == interactions.size match{
      case false =>{
        Logger.error("Request with multiple interaction with the same widget tag")
        Nil
      }
      case true =>
        decisionTree match {
          case None => Nil
          case Some(tree) => tree.getRecommendation(interactions)
        }
    }
  }
}
