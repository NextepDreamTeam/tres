package models.commons

import play.api.libs.json._

/** A class that represents an interaction of a generic user on a widget tag.
  *
  * @constructor create a new interaction with given tag and action.
  * @param widgetTag the widget tag of the interaction.
  * @param action the action of the interaction.
  * @param rid the corresponding rid of the database
  */
class Interaction(val widgetTag: WidgetTag, val action: String, val rid: Option[Object] = None) {

  override def equals (other: Any) = other match {
    case o: Interaction => widgetTag.name == o.widgetTag.name && action == o.action
    case _ => false }
}

/** Companion object of interaction class.
  *
  */
object Interaction {
  def apply(widgetTag: WidgetTag, action: String, rid: Option[Object] = None) = new Interaction(widgetTag, action, rid)

  implicit val interactionWrites = new Writes[Interaction] {
    def writes(interaction: Interaction) = Json.obj(
      "widgetTag" -> interaction.widgetTag,
      "action" -> interaction.action
    )
  }

  implicit val interactionReaders = new Reads[Interaction] {
    def reads(json: JsValue) = {
      try {
        JsSuccess(Interaction(
          (json \ "widgetTag").as[WidgetTag],
          (json \ "action").as[String]
        ))
      }catch {
        case _ : Throwable => JsError()
      }
    }
  }

  lazy val interactionsReads = new Reads[List[Interaction]] {
    def reads(json: JsValue) = {
      try{
        JsSuccess((json \ "interactions").as[List[Interaction]])
      }catch {
        case _ : Throwable => JsError()
      }
    }
  }

}