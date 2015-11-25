package controllers

import models.algorithm.{Id3Service, AlgorithmService}
import models.commons._
import models.storage.{BehaviorOdb, BehaviorDao}
import play.api.mvc._
import play.api.libs.json._


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
            val items: List[Item] = algorithmService.getRecommendation(interactions)
            Ok(Json.toJson(items)(Item.itemsWrites))
          }
        }
    }
  }
}
