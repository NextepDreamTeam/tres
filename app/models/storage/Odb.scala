package models.storage

import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientGraphFactory}
import scala.collection.JavaConverters._
import play.api.Play.current

/** Object that provides connection to OrientDb server
  *
  */
object Odb {

  /** Removes all records on the database
    *
    */
  def clearDb() = factory.getNoTx.getVertices().asScala.foreach(v => v.remove())


  /** Creates a factory for Transactional and NonTransactional database
    * getting url, username and password from configuration file
    *
    * @return a new OrientGraphFactory instance with the credentials given by
    *         configuration file
    */
  val factory = new OrientGraphFactory(
    current.configuration.getString("tresdb.url").get,
    current.configuration.getString("tresdb.username").get,
    current.configuration.getString("tresdb.password").get
  ).setupPool(1,50)

}
