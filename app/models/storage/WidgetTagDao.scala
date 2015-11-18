package models.storage

import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx
import models.commons.WidgetTag


/**
  * Created by aandelie on 17/11/15.
  */
trait WidgetTagDao {
  def getWidgetTag(rid: AnyRef)(implicit orientGraphNoTx: OrientGraphNoTx): WidgetTag
}

object WidgetTagOdb extends WidgetTagDao{
  override def getWidgetTag(rid: AnyRef)(implicit orientGraphNoTx: OrientGraphNoTx): WidgetTag = {
    val vertex: Vertex = orientGraphNoTx.getVertex(rid)
    val name: String = vertex.getProperty("name")
    WidgetTag(name,Option(rid))
  }
}
