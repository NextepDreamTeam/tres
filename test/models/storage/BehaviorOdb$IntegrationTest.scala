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

  var tagList = Tag("item:taguno") :: Tag("item:tagdue") :: Tag("item:tagtre") :: Tag("item:tagquattro"):: Nil
  var widgetTagList = WidgetTag("wtag:sport") :: WidgetTag("wtag::mountain") ::
    WidgetTag("wtag:city") :: WidgetTag("wtag::sea") :: WidgetTag("wtag::enduro") ::Nil

  var itemList = Item(tagList.head :: tagList(1) :: tagList(3) :: Nil) :: Item(tagList.head :: tagList(2) :: Nil) :: Nil

  var behaviorList =
      Behavior(
        itemList.head,
        Interaction(widgetTagList.head, "clicked") :: Interaction(widgetTagList(1), "onhover") :: Nil
      )::
      Behavior(
        itemList(1),
        Interaction(widgetTagList(2), "liked") :: Interaction(widgetTagList(3), "onhover") :: Nil
      )::
      Behavior(
        itemList(1),
        Interaction(widgetTagList.head, "clicked") :: Interaction(widgetTagList(4), "dislike") :: Nil
      ):: Nil

  var behavior = behaviorList.head

  "BehaviorOdb$IntegrationTest" should {

    "preparing enviroment test" in new WithApplication {
      implicit val txGraph: OrientGraph = Odb.factory.getTx
      implicit val noTxGraph: OrientGraphNoTx = Odb.factory.getNoTx
      //clearing database
      Odb.clearDb(txGraph)
      txGraph.commit()
      //insertion tag vertices
      tagList = tagList.map( tag => {
        val tagVertex: Vertex = txGraph.addVertex("tag", null)
        tagVertex.setProperty("name", tag.name)
        Tag(tag.name,Option(tagVertex.getId))
      })
      //inserting item vertices
      itemList = Item(tagList.head :: tagList(1) :: tagList(3) :: Nil) :: Item(tagList.head :: tagList(2) :: Nil) :: Nil
      itemList = itemList.map( item => {
        val itemVertex: Vertex = txGraph.addVertex("item", null)
        item.tags.foreach( tag =>{
          val tagVertex: Vertex = txGraph.getVertex(tag.rid.get)
          txGraph.addEdge(null, itemVertex, tagVertex, "holdsTag")
        })
        Item(item.tags,Option(itemVertex.getId))
      })
      //inserting widgetTag vertices
      widgetTagList = widgetTagList.map(widgetTag => {
        val widgetTagVertex: Vertex = txGraph.addVertex("widgetTag", null)
        widgetTagVertex.setProperty("name", widgetTag.name)
        WidgetTag(widgetTag.name,Option(widgetTagVertex.getId))
      })
      //inserting behavior vertices
      behaviorList =
        Behavior(
          itemList.head,
          Interaction(widgetTagList.head, "clicked") :: Interaction(widgetTagList(1), "onhover") :: Nil
        )::
        Behavior(
          itemList(1),
          Interaction(widgetTagList(2), "liked") :: Interaction(widgetTagList(3), "onhover") :: Nil
        )::
        Behavior(
          itemList(1),
          Interaction(widgetTagList.head, "clicked") :: Interaction(widgetTagList(4), "dislike") :: Nil
        ):: Nil
      behaviorList = behaviorList.map( behavior => {
        val behaviorVertex: Vertex = txGraph.addVertex("behavior", null)
        behavior.interactions.foreach( interaction => {
          val widgetTagVertex: Vertex = txGraph.getVertex(interaction.widgetTag.rid.get)
          val actionEdge: Edge = txGraph.addEdge(null, behaviorVertex, widgetTagVertex, "interaction")
          actionEdge.setProperty("action", interaction.action)
        })
        val itemVertex: Vertex = txGraph.getVertex(behavior.item.rid.get)
        txGraph.addEdge(null, behaviorVertex, itemVertex, "result")
        Behavior(behavior.item, behavior.interactions, Option(behaviorVertex.getId))
      })
      behavior = behaviorList.head
      txGraph.commit()
    }

    "getBehavior must returns the right Behavior" in new WithApplication {
      implicit val txGraph: OrientGraph = Odb.factory.getTx
      implicit val noTxGraph: OrientGraphNoTx = Odb.factory.getNoTx
      val result = BehaviorOdb.getBehavior(behavior.rid.get)
      result must beEqualTo(behavior)
    }

    "all must returns the right Behavior List" in new WithApplication {
      implicit val txGraph: OrientGraph = Odb.factory.getTx
      implicit val noTxGraph: OrientGraphNoTx = Odb.factory.getNoTx
      //testing method
      val result = BehaviorOdb.all()
      result.foreach( behavior => {
        behaviorList must contain(behavior)
      })
    }

  }


}
