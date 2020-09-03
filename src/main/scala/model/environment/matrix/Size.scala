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

  def apply(_weight: Int, _height: Int): Size = new Size {
    override val width: Int = _weight
    override val height: Int = _height
  }

  implicit def toSize: ((Int, Int)) => Size = p => Size(p._1, p._2)
}
