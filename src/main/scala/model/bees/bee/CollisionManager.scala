package scala.model.bees.bee

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.utility.PimpTuple._

/**
 * Object that represents a colony area
 */
object CollisionManager {

  type Range = (Int, Int)

  def collisionArea(colony1: Colony, colony2: Colony): Int = {
    val sumColony1 = (colony1.position.x, colony1.position.y).applyOperation(colony1.dimension)(_ + _)
    val diffColony1 = (colony1.position.x, colony1.position.y).applyOperation(colony1.dimension)(_ - _)
    val sumColony2 = (colony2.position.x, colony2.position.y).applyOperation(colony2.dimension)(_ + _)
    val diffColony2 = (colony2.position.x, colony2.position.y).applyOperation(colony2.dimension)(_ - _)
    val colony1XRange: Range = (diffColony1._1, sumColony1._1)
    val colony1YRange: Range = (diffColony1._2, sumColony1._2)
    val colony2XRange: Range = (diffColony2._1, sumColony2._1)
    val colony2YRange: Range = (diffColony2._2, sumColony2._2)

    colony1XRange.intersection(colony2XRange) * colony1YRange.intersection(colony2YRange)
  }





/*
  case class ColonyAreaImpl(override val center: Point , dimension: Int) extends ColonyArea{
    private var side: Int = dimension*2+1

    override def area(): Int = side^2

    override def *(x: Int): Int = this.area()*x


  }

 */
  }
