package models.storage

import com.orientechnologies.orient.core.sql.OCommandSQL

import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.{Edge, Direction, Vertex}
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import models.commons._

/**
  * Created by aandelie on 17/11/15.
  */
trait ItemDao {

  def getItem(rid: Object): Item

  def searchItem(item: Item): Option[AnyRef]

  def save(item: Item): Boolean

}

object ItemOdb extends ItemDao{

  var tagDao: TagDao = TagOdb

  override def getItem(rid: AnyRef): Item = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val itemVertex: Vertex = orientGraphNoTx.getVertex(rid)
    val holdsTagEdges: Iterable[Edge] = itemVertex.getEdges(Direction.OUT,"holdstag").asScala
    if (holdsTagEdges.isEmpty) throw new Exception("Tags for item " + rid + " must be at least one")
    val tags: List[Tag] =
      holdsTagEdges.map(e => e.getVertex(Direction.IN)).map(v => tagDao.getTag(v.getId)).toList
    Item(tags,Option(rid))
  }

  override def searchItem(item: Item): Option[AnyRef] = {
    val orientGraphNoTx = Odb.factory.getNoTx
    var conditions = "true = true " //useless condition
    item.tags.foreach(tag => {
      conditions = conditions + s"""and outE("holdsTag").inV("tag") in (select from Tag where name = "${tag.name}") """
    })
    val query =s"""select from Item where $conditions"""
    val itemSearched: OrientDynaElementIterable = orientGraphNoTx.command(new OCommandSQL(query)).execute()
    itemSearched.asScala.toList match {
      case rid :: xs => Option(rid)
      case Nil => None
    }
  }

  override def save(item: Item): Boolean = {
    val orientGraph = Odb.factory.getTx
    item.rid match {
      case Some(x) => false
      case None =>
        val itemVertex: Vertex = orientGraph.addVertex("item", null)
        //for each tag
        item.tags.foreach( tag =>{
          //search tag
          tagDao.searchTag(tag) match {
            case None => tagDao.save(tag) //if there isn't any tag, save it
            case Some(rid) => tag.rid = Option(rid) //else get rid value
          }
          //create edge from item and tag
          val tagVertex: Vertex = orientGraph.getVertex(tag.rid.get)
          orientGraph.addEdge(null,itemVertex,tagVertex,"holdstag")
        })
        orientGraph.commit()
        item.rid = Option(itemVertex.getId)
        true
    }
  }
}