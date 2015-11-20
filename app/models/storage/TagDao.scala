package models.storage

import com.tinkerpop.blueprints.Vertex

import scala.collection.JavaConverters._
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import models.commons.Tag


/** An interface that represent the access module to db for tag instance
  *
  */
trait TagDao {


  /** This method save the given tag in the database and it change it's rid value with his new rid value
    *
    * @param tag the behavior that has to be stored on database
    * @return true commit, false if is already on database
    */
  def save(tag: Tag): Boolean


  /** This method it searches the given tag on the database
    *
    * @param tag tag to be searched
    * @return return rid of the search, None if there isn't
    */
  def searchTag(tag: Tag): Option[AnyRef]


  /** This method it supply the current tag by given rid
    *
    * @param rid rid value of the tag
    * @return return the tag referred
    */
  def getTag(rid: AnyRef): Tag
}


/** A singleton that implements tag dao for orientDB
  *
  */
object TagOdb extends TagDao{


  /** This method it supply the current tag by given rid
    *
    * @param rid rid value of the tag
    * @return return the tag referred
    */
  override def getTag(rid: AnyRef): Tag = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val tagVertex = orientGraphNoTx.getVertex(rid)
    val name: String = tagVertex.getProperty("name")
    Tag(name,Option(rid))
  }


  /** This method it searches the given tag on the database
    *
    * @param tag tag to be searched
    * @return return rid of the search, None if there isn't
    */
  override def searchTag(tag: Tag): Option[AnyRef] = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val query = s"""select from Tag where name = "${tag.name}" """
    val tagSearched: OrientDynaElementIterable = orientGraphNoTx.command(new OCommandSQL(query)).execute()
    tagSearched.asScala.toList match {
      case rid :: xs => Option(rid)
      case Nil => None
    }
  }


  /** This method save the given tag in the database and it change it's rid value with his new rid value
    *
    * @param tag the behavior that has to be stored on database
    * @return true commit, false if is already on database
    */
  override def save(tag: Tag): Boolean = {
    val orientGraph = Odb.factory.getTx
    tag.rid match {
      case Some(x) => false
      case None =>
        val tagVertex: Vertex = orientGraph.addVertex("tag", null)
        tagVertex.setProperty("name", tag.name)
        orientGraph.commit()
        tag.rid = Option(tagVertex.getId)
        true
    }
  }
}
