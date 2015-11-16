package models.commons

import play.api.libs.json._

/** A class that represents an widget tag for web elements
  *
  * @constructor create a new widget tag with a given name
  * @param name the name of the widget tag
  */
class WidgetTag(val name: String) {}

/**
  *
  */
object WidgetTag {
  def apply(name: String) = new WidgetTag(name: String)

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