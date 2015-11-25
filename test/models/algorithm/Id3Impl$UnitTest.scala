package models.algorithm

import models.commons._
import org.specs2.mutable.Specification


class Id3Impl$UnitTest extends Specification {

  "Id3Impl$UnitTest" should {
    "getBehaviorsWithWTagAndAction" in {
      val b1 = Behavior(
        Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
      )
      val b2 = Behavior(
        Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil),
        Interaction(WidgetTag("wtag:sport"), "unclicked") ::
          Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
      )
      val b3 = Behavior(
        Item(Tag("item:u") :: Tag("item:d") :: Tag("item:t") :: Nil),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
      )
      val behaviorList = b1 :: b2 :: b3 :: Nil
      val result = Id3Impl.getBehaviorsWithWTagAndAction(behaviorList, "wtag:sport", "clicked")

      result mustEqual (b1 :: b3 :: Nil)
    }


    "getDistinctListOfAction" in {
      val b1 = Behavior(
        Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil),
        Interaction(WidgetTag("wtag:sport"), "yes") ::
          Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
      )
      val b2 = Behavior(
        Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
      )
      val b3 = Behavior(
        Item(Tag("item:u") :: Tag("item:d") :: Tag("item:t") :: Nil),
        Interaction(WidgetTag("wtag:sport"), "yes") ::
          Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
      )
      val behaviorList = b1 :: b2 :: b3 :: Nil
      val result = Id3Impl.getDistinctListOfActions(behaviorList, "wtag:sport")

      val list = "yes" :: "clicked" ::Nil

      result mustEqual (list)
    }

    "entropy" in {
      val b1 = Behavior(
        Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("uno")),
        Interaction(WidgetTag("wtag:sport"), "unclicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )
      val b2 = Behavior(
        Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil, Option("due")),
        Interaction(WidgetTag("wtag:sport"), "unclicked") ::
          Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
      )
      val b3 = Behavior(
        Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil, Option("uno")),
        Interaction(WidgetTag("wtag:sport"), "unclicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )
      val b4 = Behavior(
        Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("due")),
        Interaction(WidgetTag("wtag:sport"), "unclicked") ::Nil
      )
      val b5 = Behavior(
        Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil, Option("uno")),
        Interaction(WidgetTag("wtag:sport"), "unclicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )
      val b6 = Behavior(
        Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("due")),
        Interaction(WidgetTag("wtag:sport"), "clicked") :: Nil
      )



      val item1 = Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("uno"))
      val item2 = Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil, Option("due"))

      val wTag = WidgetTag("wtag::mountain", None)


      val behaviorList = b1 :: b2 :: b3 :: b4 :: b5 :: b6 :: Nil
      val itemL = item1 :: item2 :: Nil

      val result = Id3Impl.entropy(behaviorList, itemL)
      result mustEqual(1)
    }

    "gain" in {
      val b1 = Behavior(
        Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("uno")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )
      val b2 = Behavior(
        Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("uno")),
        Interaction(WidgetTag("wtag:sport"), "unclicked") ::
          Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
      )
      val b3 = Behavior(
        Item(Tag("item:u") :: Tag("item:d") :: Tag("item:t") :: Nil, Option("due")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )
      val b4 = Behavior(
        Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("uno")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
      )
      val b5 = Behavior(
        Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("uno")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )
      val b6 = Behavior(
        Item(Tag("item:u") :: Tag("item:d") :: Tag("item:t") :: Nil, Option("due")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )



      val item1 = Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("uno"))
      val item2 = Item(Tag("item:u") :: Tag("item:d") :: Tag("item:t") :: Nil, Option("due"))

      val wTag = WidgetTag("wtag:sport", None)


      val behaviorList = b1 :: b2 :: b3 :: b4 :: b5 :: b6 :: Nil
      val itemL = item1 :: item2 :: Nil

      val result = Id3Impl.gain(behaviorList, itemL, wTag)
      result mustEqual(0.109170338675599)
    }

    "gain" in {
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

      val itemList: List[Item]= no ::si ::forse :: Nil

      val result = Id3Impl.gain(trainingSet, itemList, previousCustomer)
      result mustEqual(0.10451129482395694)


    }

  }

}
