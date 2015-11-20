package models.commons

import play.api.libs.json._

/** A class that represents an interaction of a generic user on a widget tag.
  *
  * @constructor create a new interaction with given tag and action.
  * @param widgetTag the widget tag of the interaction.
  * @param action the action of the interaction.
  * @param rid the corresponding rid of the database
  */
class Interaction(val widgetTag: WidgetTag, val action: String, var rid: Option[AnyRef] = None) {


  /** A method that compare this object with a given object
    *
    * @param other an object to compare
    * @return true if other is an interaction and it match all fields
    */
  override def equals (other: Any) = other match {
    case that: Interaction => widgetTag.equals(that.widgetTag) && action.equals(that.action)
    case _ => false
  }


  /** A method that return the string representation of thw object, it prints every field
    *
    * @return the string representation of the object
    */
  override def toString: String = super.toString + s" widgetTag: $widgetTag action: $action"
}

/** Companion object of interaction class.
  *
  */
object Interaction {


  /** Constructor for the Interaction class
    *
    * @param widgetTag the widget tag of the interaction.
    * @param action the action of the interaction.
    * @param rid the corresponding rid of the database
    * @return returns a new interaction with given tag and action.
    */
  def apply(widgetTag: WidgetTag, action: String, rid: Option[AnyRef] = None) = new Interaction(widgetTag, action, rid)


  /** implicit value for json formatter for the rest output
    * it includes every field except the rid field
    * @return a specific new writes
    */
  implicit val interactionWrites = new Writes[Interaction] {
    def writes(interaction: Interaction) = Json.obj(
      "widgetTag" -> interaction.widgetTag,
      "action" -> interaction.action
    )
  }


  /** implicit value for json formatter for the rest output
    * it includes every field except the rid field
    * @return a specific new reads
    */
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


  /** implicit value for json formatter for the rest output
    * it reads a list
    * @return a specific new reads
    */
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