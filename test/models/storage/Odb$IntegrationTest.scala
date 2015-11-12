package models.storage

import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.test.FakeApplication

/**
  * Integration test for Odb class
  */
class Odb$IntegrationTest extends PlaySpec with OneAppPerSuite {

  implicit override lazy val app: FakeApplication = FakeApplication()

  "Odb" should {

    "connect to db" in {
      val graph = Odb.factory.getNoTx
      graph.getRawGraph.isClosed mustBe false
    }

  }

}
