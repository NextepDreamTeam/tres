package models.storage

import com.tinkerpop.blueprints.{Edge, Vertex}
import com.tinkerpop.blueprints.impls.orient.{OrientGraphNoTx, OrientGraph}
import models.commons._
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test.WithApplication

/**
  *
  */
@RunWith(classOf[JUnitRunner])
class BehaviorOdb$IntegrationTest extends Specification {

  var behavior = Behavior(
    Item(Tag("item:taguno") :: Tag("item:tagdue") :: Tag("item:tagtre") :: Nil),
    Interaction(WidgetTag("wtag:sport"), "clicked") :: Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
  )


  "BehaviorOdb$IntegrationTest" should {

    "getBehavior" in new WithApplication {

      val txGraph: OrientGraph = Odb.factory.getTx
      Odb.clearDb(txGraph)
      txGraph.commit()

      val behaviorVertex: Vertex = txGraph.addVertex("behavior", null)
      val itemVertex: Vertex = txGraph.addVertex("item", null)
      behavior.item.tags.map(tag => {
        val tagVertex: Vertex = txGraph.addVertex("tag", null)
        tagVertex.setProperty("name", tag.name)
        txGraph.addEdge(null, itemVertex, tagVertex, "holdsTag")
      })
      txGraph.addEdge(null, behaviorVertex, itemVertex, "result")
      behavior.interactions.foreach(interaction => {
        val widgetTagVertex: Vertex = txGraph.addVertex("widgetTag", null)
        widgetTagVertex.setProperty("name", interaction.widgetTag.name)
        val actionEdge: Edge = txGraph.addEdge(null, behaviorVertex, widgetTagVertex, "interaction")
        actionEdge.setProperty("action", interaction.action)
      })
      txGraph.commit()
      behavior = Behavior(behavior.item, behavior.interactions, Option(behaviorVertex.getId))
      val result = BehaviorOdb.getBehavior(behavior.rid.get)(Odb.factory.getNoTx)
      result must beEqualTo(behavior)
      Odb.clearDb(txGraph)
      txGraph.commit()
    }

  }


}
