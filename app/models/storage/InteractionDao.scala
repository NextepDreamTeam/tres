package models.storage

import scala.collection.JavaConverters._
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.{Direction, Edge, Vertex}
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import models.commons.{Behavior, WidgetTag, Interaction}


/** An interface that represent the access module to db for interaction instance
  *
  */
trait InteractionDao {


  /** This method save the given interaction in the database and it change it's rid value with his new rid value
    *
    * @param interaction the interaction that has to be stored on database
    * @param behavior the behavior reference
    * @return true commit, false if is already on database
    */
  def save(interaction: Interaction, behavior: Behavior): Boolean


  /** This method it searches the given interaction on the database
    *
    * @param interaction interaction to be searched
    * @return return rid of the search, None if there isn't
    */
  def searchInteraction(interaction: Interaction): Option[AnyRef]


  /** This method it supply the current interaction by given rid
    *
    * @param rid rid value of the interaction
    * @return return the interaction referred
    */
  def getInteraction(rid: AnyRef): Interaction
}


/** A singleton that implements interaction dao for orientDB
  *
  */
object InteractionOdb extends InteractionDao{

  var widgetTagDao: WidgetTagDao = WidgetTagOdb


  /** This method it supply the current interaction by given rid
    *
    * @param rid rid value of the interaction
    * @return return the interaction referred
    */
  override def getInteraction(rid: AnyRef): Interaction = {
    val orientGraphNoTx = Odb.factory.getNoTx
    val edge: Edge = orientGraphNoTx.getEdge(rid)
    val action: String = edge.getProperty("action")
    val widgetTagVertex: Vertex = edge.getVertex(Direction.IN)
    val widgetTag: WidgetTag = widgetTagDao.getWidgetTag(widgetTagVertex.getId)
    Interaction(widgetTag,action,Option(rid))
  }


  /** This method it searches the given interaction on the database
    *
    * @param interaction interaction to be searched
    * @return return rid of the search, None if there isn't
    */
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


  /** This method save the given interaction in the database and it change it's rid value with his new rid value
    *
    * @param interaction the interaction that has to be stored on database
    * @param behavior the behavior reference
    * @return true commit, false if is already on database
    */
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
        interactionEdge.setProperty("action",interaction.action)
        orientGraph.commit()
        interaction.rid = Option(interactionEdge.getId)
        true
    }
  }
}
