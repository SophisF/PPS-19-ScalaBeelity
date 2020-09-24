package scala.model.bees.bee

import scala.model.bees.bee.Colony.Colony
import scala.utility.PimpTuple._
import scala.utility.PimpInt._

/**
 * Object that provides method to check collisions between colonies.
 */
object CollisionManager {

  type Range = (Int, Int)

  /**
   * Method to finds, given a colony, all the colliding colonies with it.
   * @param colony the given colony.
   * @param colonies the list of colonies.
   * @return a list of colonies that collide with colony.
   */
  def findColliding(colony: Colony, colonies: Iterable[Colony]): Iterable[Colony] = for {
    c <- colonies
    if colony != c
    if this.checkCollision(colony, c)
  } yield c

  /**
   * Method that calculate the collision area between two colonies.
   * @param colony1 the first colony.
   * @param colony2 the second colony.
   * @return the collision area between the two colonies.
   */
  def collisionArea(colony1: Colony, colony2: Colony): Int = {
    val colony1XRange: Range = colony1.center.x.applyTwoOperations(colony1.dimension)(_ - _)(_ + _)
    val colony1YRange: Range = colony1.center.y.applyTwoOperations(colony1.dimension)(_ - _)(_ + _)
    val colony2XRange: Range = colony2.center.x.applyTwoOperations(colony2.dimension)(_ - _)(_ + _)
    val colony2YRange: Range = colony2.center.y.applyTwoOperations(colony2.dimension)(_ - _)(_ + _)

    this.calculateIntersectionArea(colony1XRange)(colony2XRange) * this.calculateIntersectionArea(colony1YRange)(colony2YRange)
  }

  /**
   * Method to check if two colony collides.
   * @param colony1 the first colony.
   * @param colony2 the second colony.
   * @tparam A a possibly specific type of colony.
   * @return a boolean, true if the two colonies collide, false otherwise.
   */
  private def checkCollision[A <: Colony](colony1: A, colony2: A): Boolean = {
    this.collisionArea(colony1, colony2) > 0
  }

  /**
   * Method to calculate the intersection area between two range.
   * @param range1 the first range.
   * @param range2 the second range.
   * @return the intersection area.
   */
  private def calculateIntersectionArea(range1: Range)(range2: Range): Int = {

    //TODO refactor

    if (range1.contains(range2)) {
      val max: Int = math.min(range1._2, range2._2)
      max - range2._1 + 1
    }
    else if (range2.contains(range1)) {
      val max: Int = math.min(range1._2, range2._2)
      max - range1._1 + 1
    }
    else 0
  }
}
