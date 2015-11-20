package models.algorithm

import models.commons.{Item, WidgetTag, Behavior}
import models.storage._

/**
  * Created by aandelie on 14/11/15.
  */
object Id3Service extends AlgorithmService {

  var behaviorDao: BehaviorDao = BehaviorOdb
  var widgetTagDao: WidgetTagDao = WidgetTagOdb
  var itemDao: ItemDao = ItemOdb
  var id3: Id3 = Id3Impl

  /**
    * Method that returns true if the models.algorithm is ready to make the recommendation
    * @return Boolean
    */
  override def ready(): Boolean = {

    true
  }

  override def start(): Unit = {
    val behaviors: List[Behavior] = behaviorDao.all()
    val able: Boolean = behaviors.size >= 100
    able match {
      case true =>
        val widgetTags: List[WidgetTag] = widgetTagDao.all()
        val items: List[Item] = itemDao.all()
        id3.start(behaviors,widgetTags,items)
    }
  }
}
