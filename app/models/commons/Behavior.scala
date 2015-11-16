package models.commons

import play.api.libs.json._

/** A class that represent the entire behavior of a generic user
  *
  * @constructor create a new behavior with given item and interactions
  * @param item the item of the behavior
  * @param interactions a list of interaction of the behavior
  */
class Behavior(val item: Item, val interactions: List[Interaction]) {}

/**
  *
  */
object Behavior {
  def apply(item: Item, interactions: List[Interaction]) = new Behavior(item: Item, interactions: List[Interaction])

  implicit val behaviorWrites = new Writes[Behavior] {
    def writes(behavior: Behavior) = Json.obj(
      "item" -> behavior.item,
      "interactions" -> behavior.interactions
    )
  }

  implicit val behaviorReads = new Reads[Behavior] {
    def reads(json: JsValue) = {
      try{
        JsSuccess(Behavior(
          (json \ "item").as[Item],
          (json \ "interactions").as[List[Interaction]]
        ))
      }
      catch {
        case _ : Throwable => JsError()
      }
    }
  }
}