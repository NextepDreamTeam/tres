package models.commons

import play.api.libs.json._

/** A class that represents a tag for Item class.
  *
  * @constructor create a new tag with given name.
  * @param name the tag's name.
  * @param rid the corresponding rid of the database
  */
class Tag(val name: String, var rid: Option[AnyRef] = None) {


  /** A method that compare this object with a given object
    *
    * @param that an object to compare
    * @return true if other is a tag and it match all fields
    */
  override def equals(that: Any): Boolean = that match {
    case that: Tag => canEqual(that) && this.hashCode == that.hashCode
    case _ => false
  }


  /**
    *
    */
  def canEqual(a: Any) = a.isInstanceOf[Tag]


  /**
    *
    */
  override def hashCode: Int = {
    val prime = 31
    var result = 1
    result = prime * result + name.hashCode
    result
  }


  /** A method that return the string representation of thw object, it prints every field
    *
    * @return the string representation of the object
    */
  override def toString: String = super.toString + s" name: $name rid: $rid "
}

/** Companion object of tag class.
  *
  */
object Tag{


  /** Constructor for the tag class
    *
    * @param name the tag's name.
    * @param rid the corresponding rid of the database
    * @return returns a new tag with given name.
    */
  def apply(name: String, rid: Option[AnyRef] = None) = new Tag(name, rid)


  /** implicit value for json formatter for the rest output
    * it includes every field except the rid field
    * @return a specific new writes
    */
  implicit val tagWrites = new Writes[Tag] {
    def writes(tag: Tag) = Json.obj(
      "name" -> tag.name
    )
  }


  /** implicit value for json formatter for the rest output
    * it includes every field except the rid field
    * @return a specific new reads
    */
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