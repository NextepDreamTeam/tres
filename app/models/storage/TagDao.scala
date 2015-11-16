package models.storage
import models.commons.Tag

import com.tinkerpop.blueprints._

import scala.collection.JavaConverters._


trait TagDao {
  /**
    * Method that return all the tags from the database
    * @return Future[List[Tag]]
    */
  def all: List[Tag]


  /**
    * Method that update a tag in the database
    * @param newTag : Tag
    * @param oldTag : Tag
    * @return Future[Boolean]
    */
  def update(newTag: Tag, oldTag: Tag) : Boolean


  /**
    * Method that removes a tag from the database
    * @param removeTag : Tag
    * @return Future[Boolean]
    */
  def remove(removeTag: Tag) : Boolean


  /**
    * Method that saves a new tag in the database
    * @param newTag : Tag
    * @return Future[Boolean]
    */
  def save(newTag: Tag) : Boolean
}

object TagOdb extends TagDao{
  /**
    * Method that return all the tags from the database
    * @return Future[List[Tag]]
    */
  override def all: List[Tag] = {
    val graph = Odb.factory.getNoTx
    val tagListVertex = graph.getVerticesOfClass("tag").asScala
    val tagList = tagListVertex map(v => Tag(v.getProperty("name")))
    tagList.toList
  }

  /**
    * Method that update a tag in the database
    * @param newTag : Tag
    * @param oldTag : Tag
    * @return Future[Boolean]
    */
  override def update(newTag: Tag, oldTag: Tag): Boolean = ???

  /**
    * Method that removes a tag from the database
    * @param removeTag : Tag
    * @return Future[Boolean]
    */
  override def remove(removeTag: Tag): Boolean = ???

  /**
    * Method that saves a new tag in the database
    * @param newTag : Tag
    * @return Future[Boolean]
    */
  override def save(newTag: Tag): Boolean = ???
}
