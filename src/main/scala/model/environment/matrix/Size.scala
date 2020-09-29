package scala.model.environment.matrix

import scala.model.environment.matrix.Size.Border
import scala.model.environment.matrix.Size.Border.Border

/**
 * Represent the size of a 2d element
 *
 * @author Paolo Baldini
 */
private[environment] trait Size {
  val width: Int
  val height: Int

  /**
   * Get the 'distance' of the specified border from the center
   *
   * @param border to which calculate the distance
   * @return the numeric distance of the border from the center
   */
  def ~(border: Border): Int = border match {
    case Border.Top => - height / 2
    case Border.Bottom => height / 2
    case Border.Left => - width / 2
    case Border.Right => width / 2
  }
}

private[environment] object Size {

  /**
   * Enumeration of the various border 'sides'
   *
   * @author Paolo Baldini
   */
  object Border extends Enumeration {
    type Border = Value
    val Top, Right, Bottom, Left = Value
  }

  /**
   * Create a new 'stand-alone' size with specified width and height
   *
   * @param _width of the size
   * @param _height of the size
   * @return the size object
   */
  def apply(_width: Int, _height: Int): Size = new Size {
    override val width: Int = _width
    override val height: Int = _height
  }

  /**
   * Implicitly convert a tuple to a Size
   *
   * @return the correspondent size of the tuple
   */
  implicit def toSize: ((Int, Int)) => Size = p => Size(p._1, p._2)
}
