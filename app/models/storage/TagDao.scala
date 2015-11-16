package models.storage
import models.commons.Tag

import scala.concurrent.Future


trait TagDao {
  /**
    * Method that return all the tags from the database
    * @return Future[List[Tag]]
    */
  def all: Future[List[Tag]]


  /**
    * Method that update a tag in the database
    * @param newTag : Tag
    * @param oldTag : Tag
    * @return Future[Boolean]
    */
  def update(newTag: Tag, oldTag: Tag) : Future[Boolean]


  /**
    * Method that removes a tag from the database
    * @param removeTag : Tag
    * @return Future[Boolean]
    */
  def remove(removeTag: Tag) : Future[Boolean]


  /**
    * Method that saves a new tag in the database
    * @param newTag : Tag
    * @return Future[Boolean]
    */
  def save(newTag: Tag) : Future[Boolean]
}

object TagOdb extends TagDao{
  /**
    * Method that return all the tags from the database
    * @return Future[List[Tag]]
    */
  override def all: Future[List[Tag]] = ???

  /**
    * Method that update a tag in the database
    * @param newTag : Tag
    * @param oldTag : Tag
    * @return Future[Boolean]
    */
  override def update(newTag: Tag, oldTag: Tag): Future[Boolean] = ???

  /**
    * Method that removes a tag from the database
    * @param removeTag : Tag
    * @return Future[Boolean]
    */
  override def remove(removeTag: Tag): Future[Boolean] = ???

  /**
    * Method that saves a new tag in the database
    * @param newTag : Tag
    * @return Future[Boolean]
    */
  override def save(newTag: Tag): Future[Boolean] = ???
}
