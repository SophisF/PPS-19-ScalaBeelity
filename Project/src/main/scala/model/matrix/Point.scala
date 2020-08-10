package scala.model.matrix

/**
 * Represent a point in a 2 dimensional matrix
 *
 * @author Paolo Baldini
 */
trait Point {
  val x: Int
  val y: Int
}
object Point {

  def apply(x: Int, y: Int): Point = new Point { override val x: Int = x; override val y: Int = y }

  /**
   * Polish notation
   */
  def ==(first: Point, second: Point): Unit = first.x == second.x && first.y == second.y

  def compare(first: Point, second: Point): Boolean = first.x match {
    case second.x => first.y < first.y
    case _ => first.x < second.x
  }

  implicit def toPoint(tuple: (Int, Int)): Point = Point(tuple._1, tuple._2)
}
