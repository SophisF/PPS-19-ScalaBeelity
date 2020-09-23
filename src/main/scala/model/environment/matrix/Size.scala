package scala.model.environment.matrix

import scala.model.environment.matrix.Size.Border
import scala.model.environment.matrix.Size.Border.Border

/**
 * Represent the size of a 2d matrix
 *
 * @author Paolo Baldini
 */
trait Size {
  val width: Int
  val height: Int

  def ~(border: Border): Int = border match {
    case Border.Top => - height / 2
    case Border.Bottom => height / 2
    case Border.Left => - width / 2
    case Border.Right => width / 2
  }
}

object Size {
  object Border extends Enumeration {
    type Border = Value
    val Top, Right, Bottom, Left = Value
  }

  def apply(_width: Int, _height: Int): Size = new Size {
    override val width: Int = _width
    override val height: Int = _height
  }

  implicit def toSize: ((Int, Int)) => Size = p => Size(p._1, p._2)
}
