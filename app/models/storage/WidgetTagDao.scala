package models.storage

import scala.collection.JavaConverters._
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import models.commons.WidgetTag


/** An interface that represent the access module to db for widget tag instance
  *
  */
trait WidgetTagDao {


  /** This method save the given widget tag in the database and it change it's rid value with his new rid value
    *
    * @param widgetTag the behavior that has to be stored on database
    * @return true commit, false if is already on database
    */
  def save(widgetTag: WidgetTag): Boolean


  /** This method it searches the given widget tag on the database
    *
    * @param widgetTag widget tag to be searched
    * @return return rid of the search, None if there isn't
    */
  def search(widgetTag: WidgetTag): Option[AnyRef]


  /** This method it supply the current widget tag by given rid
    *
    * @param rid rid value of the widget tag
    * @return return the widget tag referred
    */
  def getWidgetTag(rid: AnyRef): WidgetTag
}


/** A singleton that implements widget tag dao for orientDB
  *
  */
object WidgetTagOdb extends WidgetTagDao{


  /** This method it supply the current widget tag by given rid
    *
    * @param rid rid value of the widget tag
    * @return return the widget tag referred
    */
  override def getWidgetTag(rid: AnyRef): WidgetTag = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val vertex: Vertex = orientGraphNoTx.getVertex(rid)
    val name: String = vertex.getProperty("name")
    WidgetTag(name,Option(rid))
  }


  /** This method it searches the given widget tag on the database
    *
    * @param widgetTag widget tag to be searched
    * @return return rid of the search, None if there isn't
    */
  override def search(widgetTag: WidgetTag): Option[AnyRef] = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val query = s""" select from widgettag where name = "${widgetTag.name}" """
    val widgetTagSearched: OrientDynaElementIterable = orientGraphNoTx.command(new OCommandSQL(query)).execute()
    widgetTagSearched.asScala.toList match {
      case rid :: xs => Option(rid)
      case Nil => None
    }
  }


  /** This method save the given widget tag in the database and it change it's rid value with his new rid value
    *
    * @param widgetTag the behavior that has to be stored on database
    * @return true commit, false if is already on database
    */
  override def save(widgetTag: WidgetTag): Boolean = {
    val orientGraph = Odb.factory.getTx
    widgetTag.rid match {
      case Some(x) => false
      case None =>
        val widgetTagVertex: Vertex = orientGraph.addVertex("widgettag", null)
        widgetTagVertex.setProperty("name", widgetTag.name)
        orientGraph.commit()
        widgetTag.rid = Option(widgetTagVertex.getId)
        true
    }
  }
}
