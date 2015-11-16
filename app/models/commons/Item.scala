package models.commons

import play.api.libs.json.{Json, Writes}

/** A class that represents an Item, it will be used as a recommendation and as
  * a target value for id3 algorithm.
  *
  * @constructor create a new item with a given tags list
  * @param tags a list of tag that represents the item
  */
class Item(val tags: List[Tag]) {}

/**
  *
  */
object Item{
  def apply(tags: List[Tag]) = new Item(tags: List[Tag])

  /*implicit val itemWrites = new Writes[Item] {
    def writes(item: Item) = Json.obj(
      "tags" -> item.tags
    )
  }*/
}