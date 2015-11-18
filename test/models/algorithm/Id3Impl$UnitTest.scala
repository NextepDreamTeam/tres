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
        Item(Tag("item:u") :: Tag("item:d") :: Tag("item:t") :: Nil, Option("uno")),
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
        Item(Tag("item:u") :: Tag("item:d") :: Tag("item:t") :: Nil, Option("due")),
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

      val result = Id3Impl.gain(behaviorList, itemL, wTag)
      result mustEqual(0.109170338675599)
    }

  }

}
