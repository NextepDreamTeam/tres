package models.algorithm

/**
  * Rappresents the services of the models.algorithm
  */
trait AlgorithmService {

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
