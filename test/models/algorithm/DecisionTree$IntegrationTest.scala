package models.algorithm

import models.commons._
import org.specs2.mutable.Specification

import scala.collection.immutable.ListMap

/**
  * Created by aandelie on 24/11/15.
  */
class DecisionTree$IntegrationTest extends Specification {

  val no: Item = Item(Tag("La")::Tag("risposta")::Tag("è")::Tag("no")::Nil)
  val si: Item = Item(Tag("La")::Tag("risposta")::Tag("è")::Tag("si")::Nil)

  val outlook: WidgetTag = WidgetTag("Outlook")
  val temperature: WidgetTag = WidgetTag("Temperature")
  val humidity: WidgetTag = WidgetTag("Humidity")
  val wind: WidgetTag = WidgetTag("Wind")

  val sunny: String = "Sunny"
  val overcast: String = "Overcast"
  val rain: String = "Rain"

  val hot: String = "Hot"
  val mild: String = "Mild"
  val cool: String = "Cool"

  val high: String = "High"
  val normal: String = "Normal"

  val weak: String = "Weak"
  val strong: String = "Strong"


  val testSet: List[Behavior] =
    Behavior(no,Interaction(outlook,sunny)::Interaction(temperature,hot)::Interaction(humidity,high)::Interaction(wind,weak)::Nil):: //D1
      Behavior(no,Interaction(outlook,sunny)::Interaction(temperature,hot)::Interaction(humidity,high)::Interaction(wind,strong)::Nil):: //D2
      Behavior(si,Interaction(outlook,overcast)::Interaction(temperature,hot)::Interaction(humidity,high)::Interaction(wind,weak)::Nil):: //D3
      Behavior(si,Interaction(outlook,rain)::Interaction(temperature,mild)::Interaction(humidity,high)::Interaction(wind,weak)::Nil):: //D4
      Behavior(si,Interaction(outlook,rain)::Interaction(temperature,cool)::Interaction(humidity,normal)::Interaction(wind,weak)::Nil):: //D5
      Behavior(no,Interaction(outlook,rain)::Interaction(temperature,cool)::Interaction(humidity,normal)::Interaction(wind,strong)::Nil):: //D6
      Behavior(si,Interaction(outlook,overcast)::Interaction(temperature,cool)::Interaction(humidity,normal)::Interaction(wind,strong)::Nil):: //D7
      Behavior(no,Interaction(outlook,sunny)::Interaction(temperature,mild)::Interaction(humidity,high)::Interaction(wind,weak)::Nil):: //D8
      Behavior(si,Interaction(outlook,sunny)::Interaction(temperature,cool)::Interaction(humidity,normal)::Interaction(wind,weak)::Nil):: //D9
      Behavior(si,Interaction(outlook,rain)::Interaction(temperature,mild)::Interaction(humidity,normal)::Interaction(wind,weak)::Nil):: //D10
      Behavior(si,Interaction(outlook,sunny)::Interaction(temperature,mild)::Interaction(humidity,normal)::Interaction(wind,strong)::Nil):: //D11
      Behavior(si,Interaction(outlook,overcast)::Interaction(temperature,mild)::Interaction(humidity,high)::Interaction(wind,strong)::Nil):: //D12
      Behavior(si,Interaction(outlook,overcast)::Interaction(temperature,hot)::Interaction(humidity,normal)::Interaction(wind,weak)::Nil):: //D13
      Behavior(no,Interaction(outlook,rain)::Interaction(temperature,mild)::Interaction(humidity,high)::Interaction(wind,strong)::Nil):: //D14
      Nil

  "DecisionTree$IntegrationTest" should {
    "create" in {
      val response: Tree = DecisionTree.create(testSet)
      println(response.toString)
      1 mustEqual 1
    }

  }
}
