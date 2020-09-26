package scala.model.environment.property.source

import breeze.linalg.DenseMatrix

import scala.model.environment.matrix.Zone
import scala.model.environment.property.Property

/**
 * A property source that influence only a sub-part (zone) of the environment
 *
 * @tparam T type of property
 */
trait ZoneSource[T <: Property] extends PropertySource[T] with Zone {
  def filter: DenseMatrix[T#Variation]
}

object ZoneSource {

  /** An implementation for a zone-source of a property who is applied to a center position */
  case class Source[T <: Property](
    filter: DenseMatrix[T#Variation],
    x: Int, y: Int,
  ) extends ZoneSource[T] {

    val width: Int = filter cols
    val height: Int = filter rows
  }
}