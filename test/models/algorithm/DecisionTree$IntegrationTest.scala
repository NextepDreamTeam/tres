package models.algorithm

import models.commons._
import org.specs2.mutable.Specification
import play.api.libs.json.Json

/**
  * Created by aandelie on 24/11/15.
  */
class DecisionTree$IntegrationTest extends Specification {

  "DecisionTree$IntegrationTest" should {
    "create" in {
      //play tennis example
      val no: Item = Item(Tag("no")::Nil)
      val si: Item = Item(Tag("si")::Nil)

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


      val trainingSet: List[Behavior] =
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

      val response: Tree = DecisionTree.create(trainingSet)
      response.size mustEqual 8
    }


    "create" in {
      //house example
      val no: Item = Item(Tag("no")::Tag("noo")::Nil)
      val si: Item = Item(Tag("si")::Tag("sii")::Nil)
      val forse: Item = Item(Tag("forse")::Nil)

      val district: WidgetTag = WidgetTag("District")
      val houseType: WidgetTag = WidgetTag("HouseType")
      val income: WidgetTag = WidgetTag("Income")
      val previousCustomer: WidgetTag = WidgetTag("PreviousCustomer")

      val suburban: String = "Suburban"
      val rural: String = "Rural"
      val urban: String = "Urban"

      val detached: String = "Detached"
      val semi: String = "Semi-Detached"
      val terrace: String = "Terrace"

      val high: String = "High"
      val low: String = "Low"

      val yes: String = "Yes"
      val non: String = "No"


      val trainingSet: List[Behavior] =
        Behavior(no,Interaction(district,suburban)::Interaction(houseType,detached)::Interaction(income,high)::Interaction(previousCustomer,non)::Nil):: //D1
          Behavior(no,Interaction(district,suburban)::Interaction(houseType,detached)::Interaction(income,high)::Interaction(previousCustomer,yes)::Nil):: //D2
          Behavior(si,Interaction(district,rural)::Interaction(houseType,detached)::Interaction(income,high)::Interaction(previousCustomer,non)::Nil):: //D3
          Behavior(si,Interaction(district,urban)::Interaction(houseType,semi)::Interaction(income,low)::Interaction(previousCustomer,non)::Nil):: //D4
          Behavior(si,Interaction(district,urban)::Interaction(houseType,semi)::Interaction(income,low)::Interaction(previousCustomer,non)::Nil):: //D5
          Behavior(no,Interaction(district,urban)::Interaction(houseType,semi)::Interaction(income,low)::Interaction(previousCustomer,yes)::Nil):: //D6
          Behavior(si,Interaction(district,rural)::Interaction(houseType,semi)::Interaction(income,low)::Interaction(previousCustomer,yes)::Nil):: //D7
          Behavior(no,Interaction(district,suburban)::Interaction(houseType,terrace)::Interaction(income,high)::Interaction(previousCustomer,yes)::Nil):: //D8
          Behavior(si,Interaction(district,suburban)::Interaction(houseType,semi)::Interaction(income,low)::Interaction(previousCustomer,non)::Nil):: //D9
          Behavior(si,Interaction(district,urban)::Interaction(houseType,terrace)::Interaction(income,low)::Interaction(previousCustomer,non)::Nil):: //D10
          Behavior(si,Interaction(district,suburban)::Interaction(houseType,terrace)::Interaction(income,low)::Interaction(previousCustomer,yes)::Nil):: //D11
          Behavior(si,Interaction(district,rural)::Interaction(houseType,terrace)::Interaction(income,low)::Interaction(previousCustomer,yes)::Nil):: //D12
          Behavior(si,Interaction(district,rural)::Interaction(houseType,detached)::Interaction(income,low)::Interaction(previousCustomer,non)::Nil):: //D13
          Behavior(no,Interaction(district,urban)::Interaction(houseType,terrace)::Interaction(income,high)::Interaction(previousCustomer,yes)::Nil):: //D14
          Behavior(forse,Interaction(district,rural)::Interaction(houseType,terrace)::Interaction(income,high)::Interaction(previousCustomer,non)::Nil):: //D15
          Behavior(forse,Interaction(district,urban)::Interaction(houseType,terrace)::Interaction(income,low)::Interaction(previousCustomer,yes)::Nil):: //D16
          Behavior(forse,Interaction(district,urban)::Interaction(houseType,detached)::Interaction(income,high)::Interaction(previousCustomer,non)::Nil):: //D17
          Behavior(forse,Interaction(district,suburban)::Interaction(houseType,semi)::Interaction(income,high)::Interaction(previousCustomer,yes)::Nil):: //D18
          Behavior(forse,Interaction(district,suburban)::Interaction(houseType,semi)::Interaction(income,low)::Interaction(previousCustomer,yes)::Nil):: //D19
          Behavior(forse,Interaction(district,suburban)::Interaction(houseType,semi)::Interaction(income,low)::Interaction(previousCustomer,non)::Nil):: //D20
          Behavior(forse,Interaction(district,rural)::Interaction(houseType,terrace)::Interaction(income,low)::Interaction(previousCustomer,yes)::Nil):: //D21
          Nil


      trainingSet.map(b=> println(Json.toJson(b)))
      val response: Tree = DecisionTree.create(trainingSet)
      response.size mustEqual 28
    }


    "create" in {
      //house example
      val no: Item = Item(Tag("no")::Tag("noo")::Nil)
      val si: Item = Item(Tag("si")::Tag("sii")::Nil)
      val forse: Item = Item(Tag("forse")::Nil)

      val district: WidgetTag = WidgetTag("District")
      val houseType: WidgetTag = WidgetTag("HouseType")
      val income: WidgetTag = WidgetTag("Income")
      val previousCustomer: WidgetTag = WidgetTag("PreviousCustomer")

      val suburban: String = "Suburban"
      val rural: String = "Rural"
      val urban: String = "Urban"

      val detached: String = "Detached"
      val semi: String = "Semi-Detached"
      val terrace: String = "Terrace"

      val high: String = "High"
      val low: String = "Low"

      val yes: String = "Yes"
      val non: String = "No"


      val trainingSet: List[Behavior] =
        Behavior(no,Interaction(district,rural)::Interaction(houseType,semi)::Interaction(income,high)::Interaction(previousCustomer,yes)::Nil):: //D1
          Behavior(si,Interaction(district,rural)::Interaction(houseType,semi)::Interaction(income,high)::Interaction(previousCustomer,non)::Nil):: //D2
          Behavior(forse,Interaction(district,rural)::Interaction(houseType,semi)::Interaction(income,high)::Interaction(previousCustomer,non)::Nil):: //D2
          Nil

      val response: Tree = DecisionTree.create(trainingSet)
      response.size mustEqual 6
    }

  }
}
