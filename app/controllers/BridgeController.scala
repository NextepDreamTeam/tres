package controllers

import algorithm.AlgorithmService
import play.api._
import play.api.libs.json._
import play.api.mvc._

/** An instance that receives recommendation calls from client
  *
  */
class BridgeController extends Controller{
  this: Controller =>

  var algorithmService: AlgorithmService = new AlgorithmService()

  def ready = Action {
    val ready: Boolean = algorithmService.ready()
    Ok(Json.obj("ready" -> ready))
  }

}
