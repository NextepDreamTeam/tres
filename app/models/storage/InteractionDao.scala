package models.storage

import com.tinkerpop.blueprints.{Direction, Edge, Vertex}
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx
import models.commons.{WidgetTag, Interaction}


/**
  *
  */
trait InteractionDao {
  def getInteraction(rid: Object)(implicit orientGraphNoTx: OrientGraphNoTx): Interaction
}

object InteractionOdb extends InteractionDao{

  var widgetTagDao: WidgetTagDao = WidgetTagOdb

  override def getInteraction(rid: AnyRef)(implicit orientGraphNoTx: OrientGraphNoTx): Interaction = {
    val edge: Edge = orientGraphNoTx.getEdge(rid)
    val action: String = edge.getProperty("action")
    val widgetTagVertex: Vertex = edge.getVertex(Direction.IN)
    val widgetTag: WidgetTag = widgetTagDao.getWidgetTag(widgetTagVertex.getId)
    Interaction(widgetTag,action,Option(rid))
  }
}
