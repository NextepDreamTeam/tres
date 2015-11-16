package models.commons

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
}