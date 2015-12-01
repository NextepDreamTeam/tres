package models.algorithm

import models.commons._
import org.specs2.control.Ok
import org.specs2.mutable.Specification

/**
  * Created by aandelie on 23/11/15.
  */
class DecisionTree$UnitTest extends Specification {

  val behaviorList: List[Behavior] =
    Behavior(
      Item(Tag("tag:uno") :: Tag("tag:due") :: Tag("tag:tre") :: Nil),
      Interaction(WidgetTag("wtag:uno"), "click") :: Interaction(WidgetTag("wtag:due"), "click") :: Nil
    ) ::
      Behavior(
        Item(Tag("tag:quattro") :: Tag("tag:due") :: Tag("tag:tre") :: Nil),
        Interaction(WidgetTag("wtag:tre"), "click") :: Interaction(WidgetTag("wtag:quattro"), "like") :: Nil
      ) ::
      Behavior(
        Item(Tag("tag:quattro") :: Tag("tag:due") :: Tag("tag:tre") :: Nil),
        Interaction(WidgetTag("wtag:uno"), "onhover") :: Interaction(WidgetTag("wtag:tre"), "click") :: Nil
      ) ::
      Nil

  "DecisionTree$UnitTest" should {
    "getDistinctWidgetTagList" in {
      val widgetTagList = Nil
      val response = DecisionTree.getDistinctWidgetTagList(behaviorList)
      response.size mustEqual widgetTagList.size
      response mustEqual widgetTagList
    }
    "getDistinctItemList" in {
      val itemList =
        Item(Tag("tag:uno") :: Tag("tag:due") :: Tag("tag:tre") :: Nil) ::
          Item(Tag("tag:quattro") :: Tag("tag:due") :: Tag("tag:tre") :: Nil) ::
          Nil
      val response = DecisionTree.getDistinctItemList(behaviorList)
      response.size mustEqual itemList.size
      response mustEqual itemList
    }
    "getBehaviorsWithInteraction" in {
      val interaction = Interaction(WidgetTag("wtag:tre"),"click")
      val response = DecisionTree.getBehaviorsWithInteraction(behaviorList,interaction)
      val expected = Behavior(
        Item(Tag("tag:quattro") :: Tag("tag:due") :: Tag("tag:tre") :: Nil),
        Interaction(WidgetTag("wtag:tre"), "click") :: Interaction(WidgetTag("wtag:quattro"), "like") :: Nil
      ) ::
        Behavior(
          Item(Tag("tag:quattro") :: Tag("tag:due") :: Tag("tag:tre") :: Nil),
          Interaction(WidgetTag("wtag:uno"), "onhover") :: Interaction(WidgetTag("wtag:tre"), "click") :: Nil
        ) ::
        Nil
      response.size mustEqual expected.size
      response mustEqual expected
    }
  }
}
