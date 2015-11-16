package controllers

import algorithm.AlgorithmService
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
  val controller = new BridgeController()
  controller.algorithmService=mockAlgorithmService

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
      val body: JsValue = Json.parse("""{"item":{"tags":[{"name":"item:uno"},{"name":"item:due"},{"name":"item:tre"}]},"interactions":[{"widgetTag":{"name":"wtag:sport"},"action":"clicked"},{"widgetTag":{"name":"wtag::mountain"},"action":"onhover"}]}""")
      val response = controller.insertBehavior().apply(FakeRequest(POST, "/tres/behavior").withJsonBody(body))
      status(response) must equalTo(OK)
    }

  }
}
