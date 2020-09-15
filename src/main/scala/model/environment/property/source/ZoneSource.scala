package scala.model.environment.property.source

import breeze.linalg.DenseMatrix

import scala.model.environment.matrix.Zone
import scala.model.environment.property.Property

/**
 * A property source that influence only a sub-part of the environment
 *
 * @tparam T type of property
 *
 * @author Paolo Baldini
 */
trait ZoneSource[T <: Property] extends PropertySource[T] with Zone {
  def filter: DenseMatrix[T#Variation]
}

object ZoneSource {

  /**
   * Object for a basic filter that contains a matrix representing the gaussian 3d function.
   * It also store the center position (source) on which apply the filter.
   *
   * @author Paolo Baldini
   */
  case class Source[T <: Property](
    filter: DenseMatrix[T#Variation],
    x: Int, y: Int,
    width: Int, height: Int
  ) extends ZoneSource[T]
}