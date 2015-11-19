package models.storage

import scala.collection.JavaConverters._
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import models.commons.WidgetTag


/**
  * Created by aandelie on 17/11/15.
  */
trait WidgetTagDao {
  def save(widgetTag: WidgetTag): Boolean

  def search(widgetTag: WidgetTag): Option[AnyRef]

  def getWidgetTag(rid: AnyRef): WidgetTag
}

object WidgetTagOdb extends WidgetTagDao{
  override def getWidgetTag(rid: AnyRef): WidgetTag = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val vertex: Vertex = orientGraphNoTx.getVertex(rid)
    val name: String = vertex.getProperty("name")
    WidgetTag(name,Option(rid))
  }

  override def search(widgetTag: WidgetTag): Option[AnyRef] = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val query = s""" select from widgettag where name = "${widgetTag.name}" """
    val widgetTagSearched: OrientDynaElementIterable = orientGraphNoTx.command(new OCommandSQL(query)).execute()
    widgetTagSearched.asScala.toList match {
      case rid :: xs => Option(rid)
      case Nil => None
    }
  }

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
