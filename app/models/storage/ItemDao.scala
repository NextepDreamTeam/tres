package models.storage

import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.{Edge, Direction, Vertex}
import com.tinkerpop.blueprints.impls.orient.{OrientGraphNoTx, OrientGraph}
import models.commons._

/**
  * Created by aandelie on 17/11/15.
  */
trait ItemDao {

  def getItem(rid: Object)(implicit orientGraphNoTx: OrientGraphNoTx): Item

}

object ItemOdb extends ItemDao{

  var tagDao: TagDao = TagOdb

  override def getItem(rid: AnyRef)(implicit orientGraphNoTx: OrientGraphNoTx): Item = {
    val itemVertex: Vertex = orientGraphNoTx.getVertex(rid)
    val holdsTagEdges: Iterable[Edge] = itemVertex.getEdges(Direction.OUT,"holdstag").asScala
    if (holdsTagEdges.isEmpty) throw new Exception("Tags for item " + rid + " must be at least one")
    val tags: List[Tag] =
      holdsTagEdges.map(e => e.getVertex(Direction.IN)).map(v => tagDao.getTag(v.getId)).toList
    Item(tags,Option(rid))
  }
}