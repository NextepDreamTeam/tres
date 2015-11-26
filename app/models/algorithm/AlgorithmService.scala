package models.algorithm

import models.commons.{Item, Interaction}

/**
  * Rappresents the services of the models.algorithm
  */
trait AlgorithmService {


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
