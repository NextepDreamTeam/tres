package controllers

import models.algorithm.{AlgorithmService, Id3Service}
import models.commons._
import models.storage.{BehaviorDao, BehaviorOdb}
import play.api.libs.json._
import play.api.mvc._


/** An instance that receives recommendation calls from client
  *
  */
class BridgeController extends Controller {
  this: Controller =>

  var algorithmService: AlgorithmService = Id3Service
  var behaviorDao: BehaviorDao = BehaviorOdb

  /**
    *
    * @return
    */
  def ready() = Action{
    val ready: Boolean = algorithmService.ready()
    Ok(Json.obj("ready" -> ready))
  }

  /**
    *
    * @return
    */
  def insertBehavior() = Action{ request =>
    val jsonObject = request.body.asJson
    jsonObject match {
      case None => BadRequest("Need json")
      case Some(json) =>
        val validate: JsResult[Behavior] = json.validate[Behavior](Behavior.behaviorReads)
        validate match {
          case JsError(_) => BadRequest("Invalid json request")
          case JsSuccess(behavior, _) =>
            behaviorDao.save(behavior) match {
              case true => Created
              case false => InternalServerError
            }
        }
    }
  }

  /**
    *
    * @return
    */
  def recommendation() = Action{ request =>
    val jsonObject = request.body.asJson
    jsonObject match {
      case None => BadRequest("Need json")
      case Some(json) =>
        val validate: JsResult[List[Interaction]] = json.validate[List[Interaction]](Interaction.interactionsReads)
        validate match {
          case JsError(_) => BadRequest("Invalid json request")
          case JsSuccess(interactions, _) => {
            val ip: List[(Item, Double)] = algorithmService.getRecommendation(interactions)
            Ok(Json.toJson(ip)(Item.percentageWrites))
          }
        }
    }
  }
}
