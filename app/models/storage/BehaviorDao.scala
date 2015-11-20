package models.storage


import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.{Edge, Direction, Vertex}
import models.commons._


/** An interface that represent the access module to db for behavior instance
  *
  */
trait BehaviorDao {


  /** This method it supply the current behavior by given rid
    *
    * @param rid rid value of the behavior
    * @return return the behavior referred
    */
  def getBehavior(rid: AnyRef): Behavior


  /** This method supply all behavior stored in the database
    *
    * @return a list fo all behavior on database
    */
  def all(): List[Behavior]


  /** This method save the given behavior in the database and it change it's rid value with his new rid value
    *
    * @param behavior the behavior that has to be stored on database
    * @return true commit, false if is already on database
    */
  def save(behavior: Behavior): Boolean

}


/** A singleton that implements behavior dao for orientDB
  *
  */
object BehaviorOdb extends BehaviorDao{

  var interactionDao: InteractionDao = InteractionOdb
  var itemDao: ItemDao = ItemOdb


  /** This method it supply the current behavior by given rid
    *
    * @param rid rid value of the behavior
    * @return return the behavior referred
    */
  override def getBehavior(rid: AnyRef): Behavior = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val vertex: Vertex = orientGraphNoTx.getVertex(rid)
    val interactionsEdges: Iterable[Edge] = vertex.getEdges(Direction.OUT,"interaction").asScala
    val interactions: List[Interaction] =
      interactionsEdges.map(e => interactionDao.getInteraction(e.getId)).toList
    val itemEdges: Iterable[Edge] = vertex.getEdges(Direction.OUT,"result").asScala
    if (itemEdges.isEmpty) throw new Exception("Item for behavior " + rid + " must be one")
    val itemVertex: Vertex = itemEdges.head.getVertex(Direction.IN)
    val item: Item = itemDao.getItem(itemVertex.getId)
    Behavior(item,interactions,Option(rid))
  }


  /** This method supply all behavior stored in the database
    *
    * @return a list fo all behavior on database
    */
  override def all(): List[Behavior] = {
    val orientGraphNoTx = Odb.factory.getNoTx
    orientGraphNoTx.getVerticesOfClass("behavior").asScala.map(b => getBehavior(b.getId)).toList
  }


  /** This method save the given behavior in the database and it change it's rid value with his new rid value
    *
    * @param behavior the behavior that has to be stored on database
    * @return true commit, false if is already on database
    */
  override def save(behavior: Behavior): Boolean = {
    val orientGraph = Odb.factory.getTx
    behavior.rid match {
      case Some(x) => false //should i throw an exception?
      case None =>
        val behaviorVertex: Vertex = orientGraph.addVertex("behavior", null)
        itemDao.searchItem(behavior.item) match {
          case None => itemDao.save(behavior.item)
          case Some(rid) => behavior.item.rid = Option(rid)
        }
        val itemVertex: Vertex = orientGraph.getVertex(behavior.item.rid.get)
        behavior.rid = Option(behaviorVertex.getId)
        orientGraph.commit()
        orientGraph.addEdge(null, behaviorVertex, itemVertex, "result")
        orientGraph.commit()
        behavior.interactions.foreach( interaction => interactionDao.save(interaction, behavior) )
        true
    }
  }
}
