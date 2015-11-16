package models.storage

import models.commons.Interaction

import scala.concurrent.Future


trait InteractionDao {
  /**
    * Method that return all the interactions from the database
    * @return Future[List[Interaction]]
    */
  def all: List[Interaction]


  /**
    * Method that updates
    * @param newInteraction: Interaction
    * @param oldInteraction: Interaction
    * @return Future[Boolean]
    */
  def update(newInteraction: Interaction, oldInteraction: Interaction) : Boolean


  /**
    * Method that removes an interaction from the database
    * @param removeInteraction: Interaction
    * @return Future[Boolean]
    */
  def remove(removeInteraction: Interaction) : Boolean


  /**
    * Method that saves a new interaction in the database
    * @param newInteraction: Interaction
    * @return Future[Boolean]
    */
  def save(newInteraction: Interaction) : Boolean
}

object InteractionOdb extends InteractionDao{
  /**
    * Method that return all the interactions from the database
    * @return Future[List[Interaction]]
    */
  override def all: List[Interaction] = ???

  /**
    * Method that updates
    * @param newInteraction: Interaction
    * @param oldInteraction: Interaction
    * @return Future[Boolean]
    */
  override def update(newInteraction: Interaction, oldInteraction: Interaction): Boolean = ???

  /**
    * Method that removes an interaction from the database
    * @param removeInteraction: Interaction
    * @return Future[Boolean]
    */
  override def remove(removeInteraction: Interaction): Boolean = ???

  /**
    * Method that saves a new interaction in the database
    * @param newInteraction: Interaction
    * @return Future[Boolean]
    */
  override def save(newInteraction: Interaction): Boolean = ???
}
