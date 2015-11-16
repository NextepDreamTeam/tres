package models.commons

import play.api.libs.json._

/** A class that represent the entire behavior of a generic user
  *
  * @constructor create a new behavior with given item and interactions.
  * @param item the item of the behavior.
  * @param interactions a list of interaction of the behavior.
  * @param rid the corresponding rid of the database
  */
class Behavior(val item: Item, val interactions: List[Interaction], val rid: Option[Object] = None) {}

/** Companion object of behavior class.
  *
  */
object Behavior {
  def apply(item: Item, interactions: List[Interaction], rid: Option[Object] = None) = new Behavior(item: Item, interactions, rid)

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