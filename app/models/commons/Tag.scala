package models.commons

import play.api.libs.json._

/** A class that represents a tag for Item class.
  *
  * @constructor create a new tag with given name.
  * @param name the tag's name.
  */
class Tag(val name: String) {}

/**
  *
  */
object Tag{
  def apply(name: String) = new Tag(name: String)

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