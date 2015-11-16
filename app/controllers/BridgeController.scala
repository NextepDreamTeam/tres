package controllers

import algorithm.{Id3Service, AlgorithmService}
import models.commons._
import play.api._
import play.api.mvc._
import play.api.libs.json._


/** An instance that receives recommendation calls from client
  *
  */
class BridgeController extends Controller {
  this: Controller =>

  var algorithmService: AlgorithmService = Id3Service

  def ready() = Action {
    val ready: Boolean = algorithmService.ready()
    Ok(Json.obj("ready" -> ready))
  }

  def insertBehavior() = Action { request =>
    val jsonObject = request.body.asJson



    /*val behavior: Behavior = Behavior(
      Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil ),
      Interaction( WidgetTag("wtag:sport"), "clicked") ::
        Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
    )
    val response = Json.toJson(behavior)*/

    Ok
  }

}
