package algorithm

/**
  * Rappresents the services of the algorithm
  */
trait AlgorithmService {

  /**
    * Method that returns true if the algorithm is ready to make the recommendation
    * @return Boolean
    */
  def ready() : Boolean
}
