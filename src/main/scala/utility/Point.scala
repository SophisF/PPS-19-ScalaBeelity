package scala.utility

/**
 * Represent a point in a 2 dimensional matrix
 */
trait Point {
  val x: Int
  val y: Int
}

object Point {

  /**
   * Apply method.
   *
   * @param _x , x of point
   * @param _y , y of point
   * @return point
   */
  def apply(_x: Int, _y: Int): Point = new Point {
    override val x: Int = _x;
    override val y: Int = _y
  }

  /**
   * Polish like notation
   */
  def equals(first: Point, second: Point): Boolean = first.x == second.x && first.y == second.y


  /**
   * Compare method.
   *
   * @param first  point
   * @param second point
   * @return boolean of condition
   */
  def compare(first: Point, second: Point): Boolean = first.x match {
    case second.x => first.y < first.y
    case _ => first.x < second.x
  }

  /**
   * Conversion to point.
   *
   * @param tuple of int
   * @return point
   */
  implicit def toPoint(tuple: (Int, Int)): Point = Point(tuple._1, tuple._2)

  def toPoint(index: Int, width: Int): Point = Point(index % width, index / width)
}
