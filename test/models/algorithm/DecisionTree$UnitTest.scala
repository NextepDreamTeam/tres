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
        Item(Tag("tag:uno")::Tag("tag:due")::Tag("tag:tre")::Nil),
        Interaction(WidgetTag("wtag:uno"),"click")::Interaction(WidgetTag("wtag:due"),"click")::Nil
      ) ::
      Behavior(
        Item(Tag("tag:uno")::Tag("tag:due")::Tag("tag:tre")::Nil),
        Interaction(WidgetTag("wtag:tre"),"click")::Interaction(WidgetTag("wtag:quattro"),"click")::Nil
      ) ::
      Behavior(
        Item(Tag("tag:uno")::Tag("tag:due")::Tag("tag:tre")::Nil),
        Interaction(WidgetTag("wtag:uno"),"click")::Interaction(WidgetTag("wtag:tre"),"click")::Nil
      ) ::
      Nil

  "DecisionTree$UnitTest" should {
    "getDistinctWidgetTagList" in {
      val response = DecisionTree.getDistinctWidgetTagList(behaviorList)
      ok
    }

  }
}
