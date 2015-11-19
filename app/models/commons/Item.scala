package models.commons

import play.api.libs.json._

/** A class that represents an Item, it will be used as a recommendation and as
  * a target value for id3 models.algorithm.
  *
  * @constructor create a new item with a given tags list
  * @param tags a list of tag that represents the item
  * @param rid the corresponding rid of the database
  */
class Item(val tags: List[Tag], var rid: Option[AnyRef] = None) {
  override def equals (other: Any) = other match {
    case that: Item => tags.equals(that.tags)
    case _ => false
  }
  override def toString: String = super.toString + s" tags:$tags rid:$rid "

  def id = rid.get
}

/** Companion object of item class.
  *
  */
object Item{
  def apply(tags: List[Tag], rid: Option[AnyRef] = None) = new Item(tags, rid)

  implicit val itemWrites = new Writes[Item] {
    def writes(item: Item) = Json.obj(
      "tags" -> item.tags
    )
  }

  implicit val itemReads = new Reads[Item] {
    def reads(json: JsValue) = {
      try{
        JsSuccess(Item(
          (json \ "tags").as[List[Tag]]
        ))
      }catch {
        case _ : Throwable => JsError()
      }
    }
  }
}