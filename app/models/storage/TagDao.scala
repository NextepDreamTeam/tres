package models.storage

import com.tinkerpop.blueprints.Vertex

import scala.collection.JavaConverters._
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import models.commons.Tag


/**
  *
  */
trait TagDao {
  def save(tag: Tag): Boolean

  def searchTag(tag: Tag): Option[AnyRef]

  def getTag(rid: Object): Tag
}

object TagOdb extends TagDao{
  override def getTag(rid: AnyRef): Tag = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val tagVertex = orientGraphNoTx.getVertex(rid)
    val name: String = tagVertex.getProperty("name")
    Tag(name,Option(rid))
  }

  override def searchTag(tag: Tag): Option[AnyRef] = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val query = s"""select from Tag where name = "${tag.name}" """
    val tagSearched: OrientDynaElementIterable = orientGraphNoTx.command(new OCommandSQL(query)).execute()
    tagSearched.asScala.toList match {
      case rid :: xs => Option(rid)
      case Nil => None
    }
  }

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
