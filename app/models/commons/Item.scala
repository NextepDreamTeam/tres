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


  /** A method that compare this object with a given object
    *
    * @param other an object to compare
    * @return true if other is an item and it match all fields
    */
  override def equals (other: Any) = other match {
    case that: Item => tags.equals(that.tags)
    case _ => false
  }


  /** A method that return the string representation of thw object, it prints every field
    *
    * @return the string representation of the object
    */
  override def toString: String = super.toString + s" tags:$tags rid:$rid "


  /** the rid value is used as id value for the object
    *
    * @return the id used for the item
    */
  def id: AnyRef = rid.get
}

/** Companion object of item class.
  *
  */
object Item{


  /** Constructor for the Item class
    *
    * @param tags a list of tag that represents the item
    * @param rid the corresponding rid of the database
    * @return returns a new item with a given tags list
    */
  def apply(tags: List[Tag], rid: Option[AnyRef] = None) = new Item(tags, rid)


  /** implicit value for json formatter for the rest output
    * it includes every field except the rid field
    * @return a specific new writes
    */
  implicit val itemWrites = new Writes[Item] {
    def writes(item: Item) = Json.obj(
      "tags" -> item.tags
    )
  }


  /** implicit value for json formatter for the rest output
    * it includes every field except the rid field
    * @return a specific new reads
    */
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