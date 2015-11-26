package controllers

import models.algorithm.AlgorithmService
import models.commons._
import models.storage.BehaviorDao
import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json._
import play.api.test.Helpers._
import play.api.test._

/** Unit tests for BridgeController class
  *
  */
@RunWith(classOf[JUnitRunner])
class BridgeController$UnitTest extends Specification with Mockito {


  val mockAlgorithmService = mock[AlgorithmService]
  val mockBehaviorDao = mock[BehaviorDao]
  val controller = new BridgeController()
  controller.algorithmService = mockAlgorithmService
  controller.behaviorDao = mockBehaviorDao

  "BridgeController" should {

    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boomBaby!!!")) must beSome.which (status(_) == NOT_FOUND)
    }

    "ready must responds false" in new WithApplication{
      mockAlgorithmService.ready returns false
      val response = controller.ready().apply(FakeRequest(GET, "/tres/ready"))//route(FakeRequest(GET, "/tres/ready")).get
      val expected: JsValue = Json.obj("ready" -> false)
      status(response) must equalTo(OK)
      contentType(response) must beSome.which(_ == "application/json")
      contentAsJson(response) must equalTo(expected)
    }

    "ready must responds true" in new WithApplication{
      mockAlgorithmService.ready returns true
      val response = controller.ready().apply(FakeRequest(GET, "/tres/ready"))//route(FakeRequest(GET, "/tres/ready")).get
      val expected: JsValue = Json.obj("ready" -> true)
      status(response) must equalTo(OK)
      contentType(response) must beSome.which(_ == "application/json")
      contentAsJson(response) must equalTo(expected)
    }

    "insertBehavior must response bad request without json body" in new WithApplication() {
      mockAlgorithmService.ready returns false
      val response = controller.insertBehavior().apply(FakeRequest(POST, "/tres/behavior"))
      status(response) must equalTo(BAD_REQUEST)
    }

    "insertBehavior must response bad request with invalid json body" in new WithApplication {
      mockAlgorithmService.ready returns false
      val body: JsValue = Json.parse("""{"invalid":"json"}""")
      val response = controller.insertBehavior().apply(FakeRequest(POST, "/tres/behavior").withJsonBody(body))
      status(response) must equalTo(BAD_REQUEST)
    }

    "insertBehavior must response ok with valid json body" in new WithApplication {
      mockAlgorithmService.ready returns false
      mockBehaviorDao.all returns Nil
      val behavior: Behavior = Behavior(
        Item(Tag("item:uno")::Tag("item:due")::Tag("item:tre")::Nil),
        Interaction(WidgetTag("wtag:sport"),"clicked") :: Interaction(WidgetTag("wtag::mountain"),"onhover") :: Nil
      )
      mockBehaviorDao.save(behavior: Behavior) returns true
      val body: JsValue = Json.parse("""{"item":{"tags":[{"name":"item:uno"},{"name":"item:due"},{"name":"item:tre"}]},"interactions":[{"widgetTag":{"name":"wtag:sport"},"action":"clicked"},{"widgetTag":{"name":"wtag::mountain"},"action":"onhover"}]}""")
      val response = controller.insertBehavior().apply(FakeRequest(POST, "/tres/behavior").withJsonBody(body))
      status(response) must equalTo(CREATED)
    }

    "insertBehavior must response Internal Server Error with valid json body" in new WithApplication {
      mockAlgorithmService.ready returns false
      mockBehaviorDao.all returns Nil
      val behavior: Behavior = Behavior(
        Item(Tag("item:uno")::Tag("item:due")::Tag("item:tre")::Nil),
        Interaction(WidgetTag("wtag:sport"),"clicked") :: Interaction(WidgetTag("wtag::mountain"),"onhover") :: Nil
      )
      mockBehaviorDao.save(behavior: Behavior) returns false
      val body: JsValue = Json.parse("""{"item":{"tags":[{"name":"item:uno"},{"name":"item:due"},{"name":"item:tre"}]},"interactions":[{"widgetTag":{"name":"wtag:sport"},"action":"clicked"},{"widgetTag":{"name":"wtag::mountain"},"action":"onhover"}]}""")
      val response = controller.insertBehavior().apply(FakeRequest(POST, "/tres/behavior").withJsonBody(body))
      status(response) must equalTo(INTERNAL_SERVER_ERROR)
    }

    "recommendation must response bad request without json body" in new WithApplication {
      mockAlgorithmService.ready returns false
      val response = controller.recommendation().apply(FakeRequest(POST, "/tres/recommendation"))
      status(response) must equalTo(BAD_REQUEST)
    }

    "recommendation must response bad request with invalid json body" in new WithApplication {
      mockAlgorithmService.ready returns false
      val body: JsValue = Json.parse("""{"invalid":"json"}""")
      val response = controller.recommendation().apply(FakeRequest(POST, "/tres/recommendation").withJsonBody(body))
      status(response) must equalTo(BAD_REQUEST)
    }

    "recommendation must response ok with valid json body" in new WithApplication {
      mockAlgorithmService.ready returns true
      val items = Item(Tag("uno")::Tag("tre")::Nil)::Item(Tag("due")::Tag("tre")::Nil)::Nil map( i => (i,0.01))
      val interactions = Interaction(WidgetTag("wtag:sport"),"clicked")::Interaction(WidgetTag("wtag::mountain"),"onhover")::Nil
      mockAlgorithmService.getRecommendation(interactions) returns items
      val body: JsValue = Json.parse("""{"interactions":[{"widgetTag":{"name":"wtag:sport"},"action":"clicked"},{"widgetTag":{"name":"wtag::mountain"},"action":"onhover"}]}""")
      val expected = Json.parse("""{"items":[{"item":{"tags":[{"name":"uno"},{"name":"tre"}]},"percentage":0.01},{"item":{"tags":[{"name":"due"},{"name":"tre"}]},"percentage":0.01}]}""")
      val response = controller.recommendation().apply(FakeRequest(POST, "/tres/recommendation").withJsonBody(body))
      contentAsJson(response) must equalTo(expected)
      status(response) must equalTo(OK)
    }


  }
}
