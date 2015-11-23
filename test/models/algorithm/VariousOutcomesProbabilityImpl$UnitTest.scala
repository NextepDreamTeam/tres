package models.algorithm

import models.commons._
import org.specs2.mutable.Specification

/**
  * Created by bsuieric on 23/11/15.
  */
class VariousOutcomesProbabilityImpl$UnitTest extends Specification {

  "VariousOutcomesProbabilityImpl$UnitTest" should {
    "probabilityForItemsInBehaviors" in {
      val b1 = Behavior(
        Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("uno")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )
      val b2 = Behavior(
        Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil, Option("uno")),
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
        Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil, Option("uno")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )
      val b6 = Behavior(
        Item(Tag("item:u") :: Tag("item:d") :: Tag("item:t") :: Nil, Option("due")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )



      val item1 = Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("uno"))
      val item2 = Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil, Option("due"))

      val wTag = WidgetTag("wtag:sport", None)


      val behaviorList = b1 :: b2 :: b3 :: b4 :: b5 :: b6 :: Nil
      val itemL = item1 :: item2 :: Nil
      val result = VariousOutcomesProbabilityImpl.probabilityForItemsInBehaviors(behaviorList, itemL)
      result mustEqual((item1, "66%")::(item2, "33%") :: Nil)
    }

    "probabilityForItemWithWidgetTagAndAction" in {
      val b1 = Behavior(
        Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("uno")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )
      val b2 = Behavior(
        Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil, Option("uno")),
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
        Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil, Option("uno")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )
      val b6 = Behavior(
        Item(Tag("item:u") :: Tag("item:d") :: Tag("item:t") :: Nil, Option("due")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )
      val b7 = Behavior(
        Item(Tag("item:u") :: Tag("item:d") :: Tag("item:t") :: Nil, Option("due")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "hover") :: Nil
      )



      val item1 = Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("uno"))
      val item2 = Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil, Option("due"))

      val wTag = WidgetTag("wtag:sport", None)


      val behaviorList = b1 :: b2 :: b3 :: b4 :: b5 :: b6 :: b7 :: Nil
      val itemL = item1 :: item2 :: Nil

      val result = VariousOutcomesProbabilityImpl.probabilityForItemWithWidgetTagAndAction(behaviorList, itemL, wTag, "clicked")
      result mustEqual((item1, "50%")::(item2, "50%") :: Nil)
    }

  }
}
