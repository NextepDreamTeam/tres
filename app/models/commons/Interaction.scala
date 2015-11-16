package models.commons

/** A class that represents an interaction of a generic user on a widget tag.
  *
  * @constructor create a new interaction with given tag and action.
  * @param widgetTag the widget tag of the interaction.
  * @param action the action of the interaction.
  */
class Interaction(val widgetTag: WidgetTag, val action: String) {}

/**
  *
  */
object Interaction {
  def apply(widgetTag: WidgetTag, action: String) = new Interaction(widgetTag: WidgetTag, action: String)
}