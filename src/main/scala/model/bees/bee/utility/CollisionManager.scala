package scala.model.bees.bee.utility

import scala.model.bees.bee.Colony.Colony
import scala.utility.PimpInt._
import scala.utility.PimpTuple._
import scala.utility.Point
import scala.utility.TypeUtilities.Range

/**
 * Object that provides method to check collisions between colonies.
 */
object CollisionManager {

  /**
   * Method to keep a colony inside the border of the environment.
   * @param center the center of the colony.
   * @param dimension the dimension of the colony.
   * @param width the width of the environment.
   * @param height the height of the environment.
   * @return a point as the new center of the colony.
   */
  def keepInside(center: Point, dimension: Int, width: Int, height: Int): Point = {

    /**
     * Method to calculate a valid coordinate for the center of the colony.
     * @param coordinate the value of a coordinate, x or y.
     * @param envDimension the environment dimension, width or height respectively.
     * @return the coordinate, x or y, of the new center of the colony.
     */
    def calculateCoordinate(coordinate: Int, envDimension: Int): Int = {
      if(coordinate - dimension < 0) dimension else
      if(coordinate + dimension >= envDimension) envDimension - dimension
      else coordinate
    }

    Point(calculateCoordinate(center.x, width), calculateCoordinate(center.y, height))

  }

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

    /**
     * Method to calculate the spatial range occupied by a colony on one axis by
     * applying to operation to an integer value.
     * @param center the center of the colony.
     * @param dimension the dimension of the colony.
     * @return the range occupied by the colony.
     */
    def calculateRange(center: Int, dimension: Int): Range = center.applyTwoOperations(dimension)(_ - _)(_ + _)

    val colony1XRange: Range = calculateRange(colony1.center.x, colony1.dimension)
    val colony1YRange: Range = calculateRange(colony1.center.y, colony1.dimension)
    val colony2XRange: Range = calculateRange(colony2.center.x, colony2.dimension)
    val colony2YRange: Range = calculateRange(colony2.center.y, colony2.dimension)

    this.calculateOverlappingOnAxis(colony1XRange)(colony2XRange) *
      this.calculateOverlappingOnAxis(colony1YRange)(colony2YRange)
  }

  /**
   * Method to check if two colony collides.
   * @param colony1 the first colony.
   * @param colony2 the second colony.
   * @return a boolean, true if the two colonies collide, false otherwise.
   */
  private def checkCollision(colony1: Colony, colony2: Colony): Boolean = {
    this.collisionArea(colony1, colony2) > 0
  }

  /**
   * Method to calculate the intersection area between two range.
   * @param range1 the first range.
   * @param range2 the second range.
   * @return the intersection area.
   */
  private def calculateOverlappingOnAxis(range1: Range)(range2: Range): Int = {

    /**
     * Inner function to calculate the intersection area.
     * @param container the range that contains the other.
     * @param contained the range that is contained.
     * @return the intersection area.
     */
    def calculateOverlapping(container: Range, contained: Range): Int ={
      val max: Int = math.min(container._2, contained._2)
      max - contained._1 + 1
    }

    if (range1.contains(range2)) calculateOverlapping(range1, range2)
    else if (range2.contains(range1)) calculateOverlapping(range2, range1)
    else 0
  }
}
