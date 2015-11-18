package models.storage

import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx
import models.commons.Tag


/**
  *
  */
trait TagDao {
  def getTag(rid: Object)(implicit orientGraphNoTx: OrientGraphNoTx): Tag
}

object TagOdb extends TagDao{
  override def getTag(rid: AnyRef)(implicit orientGraphNoTx: OrientGraphNoTx): Tag = {
    val tagVertex = orientGraphNoTx.getVertex(rid)
    val name: String = tagVertex.getProperty("name")
    Tag(name,Option(rid))
  }
}
