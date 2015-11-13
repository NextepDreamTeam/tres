package algorithm

import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._


/**
  *
  */
trait Id3{
  def entropy(list: List[Int]): Double
}



object Id3Impl extends Id3 {
  def entropy()
}
