package controllers

import algorithm.AlgorithmService
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.{AfterEach, BeforeEach}
import play.api.libs.json.{JsValue, Json}
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}
import org.specs2.mock._

/** Unit tests for BridgeController class
  *
  */
@RunWith(classOf[JUnitRunner])
class BridgeController$UnitTest extends Specification with BeforeEach with AfterEach with Mockito {

  override protected def before: Any = {

  }

  override protected def after: Any = {

  }

  "BridgeController" should {

    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boomBaby!!!")) must beSome.which (status(_) == NOT_FOUND)
    }

    "ready must responds false" in new WithApplication{
      val mockAlgorithmService = mock[AlgorithmService]
      mockAlgorithmService.ready returns false
      val controller = new BridgeController()
      controller.algorithmService=mockAlgorithmService
      val response = controller.ready().apply(FakeRequest(GET, "/tres/ready"))//route(FakeRequest(GET, "/tres/ready")).get
      val expected: JsValue = Json.obj("ready" -> false)
      status(response) must equalTo(OK)
      contentType(response) must beSome.which(_ == "application/json")
      contentAsJson(response) must equalTo(expected)
    }

    "ready must responds true" in new WithApplication{
      val mockAlgorithmService = mock[AlgorithmService]
      mockAlgorithmService.ready returns true
      val controller = new BridgeController()
      controller.algorithmService=mockAlgorithmService
      val response = controller.ready().apply(FakeRequest(GET, "/tres/ready"))//route(FakeRequest(GET, "/tres/ready")).get
      val expected: JsValue = Json.obj("ready" -> true)
      status(response) must equalTo(OK)
      contentType(response) must beSome.which(_ == "application/json")
      contentAsJson(response) must equalTo(expected)
    }

  }
}
