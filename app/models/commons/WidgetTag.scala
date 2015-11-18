package models.commons

import play.api.libs.json._

/** A class that represents an widget tag for web elements
  *
  * @constructor create a new widget tag with a given name
  * @param name the name of the widget tag
  * @param rid the corresponding rid of the database
  */
class WidgetTag(val name: String, val rid: Option[AnyRef] = None) {
  override def equals (other: Any) = other match {
    case that: WidgetTag => name.equals(that.name)
    case _ => false
  }
  override def toString: String = super.toString + s" name: $name rid: $rid"
}

/** Companion object of widget tag class.
  *
  */
object WidgetTag {
  def apply(name: String, rid: Option[AnyRef] = None) = new WidgetTag(name, rid)

  implicit val widgetTagWrites = new Writes[WidgetTag] {
    def writes(widgetTag: WidgetTag) = Json.obj(
      "name" -> widgetTag.name
    )
  }

  implicit val widgetTagReads = new Reads[WidgetTag] {
    def reads(json: JsValue) = {
      try{
        JsSuccess(new WidgetTag(
          (json \ "name").as[String]
        ))
      }catch {
        case _ : Throwable => JsError()
      }
    }
  }
}