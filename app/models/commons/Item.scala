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
    * @param that an object to compare
    * @return true if other is an item and it match all fields
    */
  override def equals (that: Any) = that match {
    case that: Item => canEqual(that) && this.hashCode == that.hashCode
    case _ => false
  }


  /**
    *
    */
  def canEqual(a: Any) = a.isInstanceOf[Item]


  /**
    *
    */
  override def hashCode: Int = {
    val prime = 31
    var result = 1
    tags.foreach(tag => result = prime * result + tag.hashCode)
    result
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

  implicit val itemsWrites = new Writes[List[Item]] {
    def writes(items: List[Item]) = Json.obj(
      "items" -> items
    )
  }

  implicit val outputWrites = new Writes[(Item,Double)] {
    def writes(out: (Item,Double)) = Json.obj(
      "item" -> out._1,
      "percentage" -> out._2
    )
  }

  implicit val percentageWrites = new Writes[List[(Item,Double)]] {
    def writes(ip: List[(Item, Double)]) = Json.obj(
      "items" -> ip
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