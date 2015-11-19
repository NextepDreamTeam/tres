package models.storage

import scala.collection.JavaConverters._
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.{Direction, Edge, Vertex}
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import models.commons.{Behavior, WidgetTag, Interaction}


/**
  *
  */
trait InteractionDao {
  def save(interaction: Interaction, behavior: Behavior): Boolean

  def searchInteraction(interaction: Interaction): Option[AnyRef]

  def getInteraction(rid: Object): Interaction
}

object InteractionOdb extends InteractionDao{

  var widgetTagDao: WidgetTagDao = WidgetTagOdb

  override def getInteraction(rid: AnyRef): Interaction = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val edge: Edge = orientGraphNoTx.getEdge(rid)
    val action: String = edge.getProperty("action")
    val widgetTagVertex: Vertex = edge.getVertex(Direction.IN)
    val widgetTag: WidgetTag = widgetTagDao.getWidgetTag(widgetTagVertex.getId)
    Interaction(widgetTag,action,Option(rid))
  }

  override def searchInteraction(interaction: Interaction): Option[AnyRef] = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val query =
      s"""select from Interaction where inV("widgettag") in (select from widgettag where name = "${interaction.widgetTag.name}") """
    val interactionSearched: OrientDynaElementIterable = orientGraphNoTx.command(new OCommandSQL(query)).execute()
    interactionSearched.asScala.toList match {
      case rid :: xs => Option(rid)
      case Nil => None
    }
  }

  override def save(interaction: Interaction, behavior: Behavior): Boolean = {
    val orientGraph = Odb.factory.getTx
    interaction.rid match {
      case Some(x) => false
      case None =>
        widgetTagDao.search(interaction.widgetTag) match {
          case None => widgetTagDao.save(interaction.widgetTag)
          case Some(rid) => interaction.widgetTag.rid = Option(rid)
        }
        val behaviorVertex: Vertex = orientGraph.getVertex(behavior.rid.get)
        val widgetTagVertex: Vertex = orientGraph.getVertex(interaction.widgetTag.rid.get)
        val interactionEdge: Edge = orientGraph.addEdge(null, behaviorVertex, widgetTagVertex, "interaction")
        orientGraph.commit()
        interaction.rid = Option(interactionEdge.getId)
        true
    }
  }
}
