package models.storage

import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.{Edge, Direction, Vertex}
import com.tinkerpop.blueprints.impls.orient.{OrientVertexType, OrientGraph, OrientGraphNoTx}
import models.commons._


/** An interface that represent the access module to db for behavior instance
  *
  */
trait BehaviorDao {

  def getBehavior(rid: Object)(implicit orientGraphNoTx: OrientGraphNoTx): Behavior

  def all()(implicit orientGraphNoTx: OrientGraphNoTx): List[Behavior]

  def save(behavior: Behavior)(implicit orientGraph: OrientGraph): Boolean

}

object BehaviorOdb extends BehaviorDao{

  var interactionDao: InteractionDao = InteractionOdb
  var itemDao: ItemDao = ItemOdb

  override def getBehavior(rid: AnyRef)(implicit orientGraphNoTx: OrientGraphNoTx): Behavior = {
    val vertex: Vertex = orientGraphNoTx.getVertex(rid)
    val interactionsEdges: Iterable[Edge] = vertex.getEdges(Direction.OUT,"interaction").asScala
    val interactions: List[Interaction] =
      interactionsEdges.map(e => interactionDao.getInteraction(e.getId)).toList
    val itemEdges: Iterable[Edge] = vertex.getEdges(Direction.OUT,"result").asScala
    if (itemEdges.isEmpty) throw new Exception("Item for behavior " + rid + " must be one")
    val itemVertex: Vertex = itemEdges.head.getVertex(Direction.IN)
    val item: Item = itemDao.getItem(itemVertex.getId)(orientGraphNoTx)
    Behavior(item,interactions,Option(rid))
  }

  override def all()(implicit orientGraphNoTx: OrientGraphNoTx): List[Behavior] = {
    orientGraphNoTx.getVerticesOfClass("behavior").asScala.map(b => getBehavior(b.getId)).toList
  }

  override def save(behavior: Behavior)(implicit orientGraph: OrientGraph): Boolean = {
    behavior.rid match {
      case Some(x) => false //should i throw an exception?
      case None => {
        val behaviorVertex: OrientVertexType = orientGraph.createVertexType("behavior")
        //cercare l'item
        //se non lo trovo creo l'item
        //se lo trovo tengo il riferimento
        //creo l'edge con il riferimento dell'item

        //per ogni tag
        //cerco il tag
        //se non lo trovo creo il tag
        //se lo trovo tengo il riferimento
        //creo l'edge con il riferimento del tag
        true
      }
    }
  }
}
