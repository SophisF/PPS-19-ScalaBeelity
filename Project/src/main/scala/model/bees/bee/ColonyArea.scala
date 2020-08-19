package scala.model.bees.bee

import scala.model.environment.matrix.Point

/**
 * Object that represents a colony area
 */
object ColonyArea {

  /**
   * Trait that represents a colony area
   */
  trait ColonyArea{
    val center: Point
    def area(): Int
    def *(x: Int): Int

  }

  /**
   * Class that represents a colony area
   * @param center of the colony, overlap with the position of the queen
   * @param dimension is the distance between the center and the side of the square
   */
  case class ColonyAreaImpl(override val center: Point , dimension: Int) extends ColonyArea{
    private var side: Int = dimension*2+1

    override def area(): Int = side^2

    override def *(x: Int): Int = this.area()*x


  }
  }
