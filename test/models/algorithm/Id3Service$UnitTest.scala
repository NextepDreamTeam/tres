package models.algorithm

import models.commons._
import org.specs2.mutable.Specification

/**
  * Created by bsuieric on 25/11/15.
  */
class Id3Service$UnitTest extends Specification {

  "Id3Service$UnitTest" should {
    "getRecommendation" in {
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

      val tree: Tree = DecisionTree.create(trainingSet)
      val is: List[Interaction] = Interaction(district,"ula")::Interaction(houseType,semi)::Interaction(income,high)::Interaction(previousCustomer,"mia")::Nil
      val response = tree.getRecommendation(is)
      response.size mustEqual(8)
    }

    "getRecommendation" in {
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

      val tree: Tree = DecisionTree.create(trainingSet)
      val is = Interaction(district,rural)::Interaction(houseType,semi)::Interaction(income,high)::Interaction(previousCustomer,non)::Nil
      val response = tree.getRecommendation(is)
      response.size mustEqual 2
    }

    "getRecommendation" in {
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

      val tree: Tree = DecisionTree.create(trainingSet)
      val is = Interaction(district,"null")::Interaction(houseType,"null")::Interaction(income,"null")::Interaction(previousCustomer,yes)::Nil
      val response = tree.getRecommendation(is)
      response.size mustEqual 1
    }

    "getRecommendation" in {
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

      val tree: Tree = DecisionTree.create(trainingSet)
      val is :List[Interaction]= Nil
      val response = tree.getRecommendation(is)
      response.size mustEqual 3
    }

  }
}
