package controllers

import algorithm.{Id3Service, AlgorithmService}
import models.commons._
import play.api.mvc._
import play.api.libs.json._


/** An instance that receives recommendation calls from client
  *
  */
class BridgeController extends Controller {
  this: Controller =>

  var algorithmService: AlgorithmService = Id3Service

  /***
    *
    * @return
    */
  def ready() = Action {
    val ready: Boolean = algorithmService.ready()
    Ok(Json.obj("ready" -> ready))
  }

  /***
    *
    * @return
    */
  def insertBehavior() = Action { request =>
    val jsonObject = request.body.asJson
    jsonObject match {
      case Some(json) =>
        val validate: JsResult[Behavior] = json.validate[Behavior](Behavior.behaviorReads)
        validate match {
          case JsSuccess(behavior, _) => Ok("Valid json")
          case e: JsError => BadRequest("Invalid json request")
        }
      case None =>
        BadRequest("Need json")
    }
  }

}
