package models.commons

import play.api.libs.json._

/** A class that represents an widget tag for web elements
  *
  * @constructor create a new widget tag with a given name
  * @param name the name of the widget tag
  * @param rid the corresponding rid of the database
  */
class WidgetTag(val name: String, var rid: Option[AnyRef] = None) {


  /** A method that compare this object with a given object
    *
    * @param other an object to compare
    * @return true if other is a widget tag and it match all fields
    */
  override def equals (other: Any) = other match {
    case that: WidgetTag => name.equals(that.name)
    case _ => false
  }


  /** A method that return the string representation of thw object, it prints every field
    *
    * @return the string representation of the object
    */
  override def toString: String = super.toString + s" name: $name rid: $rid"
}

/** Companion object of widget tag class.
  *
  */
object WidgetTag {


  /** Constructor for the widget tag class
    *
    * @param name the name of the widget tag
    * @param rid the corresponding rid of the database
    * @return returns a new widget tag with a given name
    */
  def apply(name: String, rid: Option[AnyRef] = None) = new WidgetTag(name, rid)


  /** implicit value for json formatter for the rest output
    * it includes every field except the rid field
    * @return a specific new writes
    */
  implicit val widgetTagWrites = new Writes[WidgetTag] {
    def writes(widgetTag: WidgetTag) = Json.obj(
      "name" -> widgetTag.name
    )
  }


  /** implicit value for json formatter for the rest output
    * it includes every field except the rid field
    * @return a specific new reads
    */
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