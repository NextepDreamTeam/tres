package models.storage

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/** Integration test for Odb class
  *
  */
@RunWith(classOf[JUnitRunner])
class Odb$IntegrationTest extends Specification {

  //implicit override lazy val app: FakeApplication = FakeApplication()

  "Odb" should {

    "connect to db" in new WithApplication{
      val graph = Odb.factory.getNoTx
      graph.getRawGraph.isClosed must beEqualTo(false)
    }

  }

}
