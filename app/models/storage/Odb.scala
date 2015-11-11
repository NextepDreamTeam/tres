package models.storage

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import play.api.Play.current

/**
  * Created by aandelie on 11/11/15.
  */
object Odb {

  def factory = new OrientGraphFactory(
    current.configuration.getString("blueprints.orientdb.url").get,
    current.configuration.getString("blueprints.orientdb.username").get,
    current.configuration.getString("blueprints.orientdb.password").get
  ).setupPool(1,10)

}
