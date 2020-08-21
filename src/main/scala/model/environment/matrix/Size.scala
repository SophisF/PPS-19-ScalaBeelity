package scala.model.environment.matrix

import scala.model.environment.matrix.Size.Border

/**
 * Represent the size of a 2d matrix
 *
 * @author Paolo Baldini
 */
trait Size {
  val width: Int
  val height: Int

  def ~(border: Border): Int = border match {
    case Size.Top => - height / 2
    case Size.Bottom => height / 2
    case Size.Left => - width / 2
    case Size.Right => width / 2
  }
}
object Size extends Enumeration {
  type Border = Value
  val Top, Right, Bottom, Left = Value
}
