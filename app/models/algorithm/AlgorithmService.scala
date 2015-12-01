package models.algorithm

import models.commons.{WidgetTag, Item, Interaction}

/**
  * Rappresents the services of the models.algorithm
  */
trait AlgorithmService {
  def getNextAnswer(interactions: List[Interaction]): Option[WidgetTag]


  /**
    *
    * @param interactions
    * @return
    */
  def getRecommendation(interactions: List[Interaction]): List[(Item, Double)]


  /**
    * Method that returns true if the models.algorithm is ready to make the recommendation
    * @return Boolean
    */
  def ready() : Boolean

  /**
    *
    */
  def start()
}
