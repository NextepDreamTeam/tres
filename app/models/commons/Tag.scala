package models.commons

import play.api.libs.json._

/** A class that represents a tag for Item class.
  *
  * @constructor create a new tag with given name.
  * @param name the tag's name.
  * @param rid the corresponding rid of the database
  */
class Tag(val name: String, var rid: Option[AnyRef] = None) {
  override def equals(other: Any): Boolean = other match {
    case that: Tag => name.equals(that.name)
    case _ => false
  }

  override def toString: String = super.toString + s" name: $name rid: $rid "
}

/** Companion object of tag class.
  *
  */
object Tag{
  def apply(name: String, rid: Option[AnyRef] = None) = new Tag(name, rid)

  implicit val tagWrites = new Writes[Tag] {
    def writes(tag: Tag) = Json.obj(
      "name" -> tag.name
    )
  }

  implicit val tagReads = new Reads[Tag] {
    def reads(json: JsValue) = {
      try{
        JsSuccess(Tag(
          (json \ "name").as[String]
        ))
      }catch {
        case _ : Throwable => JsError()
      }
    }
  }
}