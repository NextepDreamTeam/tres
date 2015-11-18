package models.algorithm

import models.commons._
import org.specs2.mutable.Specification

/**
  * Created by bsuieric on 17/11/15.
  */
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
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
      )
      val b2 = Behavior(
        Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil, Option("uno")),
        Interaction(WidgetTag("wtag:sport"), "unclicked") ::
          Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
      )
      val b3 = Behavior(
        Item(Tag("item:u") :: Tag("item:d") :: Tag("item:t") :: Nil, Option("due")),
        Interaction(WidgetTag("wtag:sport"), "clicked") ::
          Interaction(WidgetTag("wtag::mountain"), "onhover") :: Nil
      )
      val item1 = Item(Tag("item:uno") :: Tag("item:due") :: Tag("item:tre") :: Nil, Option("uno"))
      val item2 = Item(Tag("item:1") :: Tag("item:2") :: Tag("item:2") :: Nil, Option("due"))
      val behaviorList = b1 :: b2 :: b3 :: Nil
      val itemL = item1 :: item2 :: Nil

      val result = Id3Impl.entropy(behaviorList, itemL)
      result mustEqual(0.9182958340544896)
    }

  }

}
