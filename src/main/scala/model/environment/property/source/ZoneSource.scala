package scala.model.environment.property.source

import scala.model.environment.matrix.Zone
import scala.model.environment.property.Property
import scala.utility.DenseMatrixHelper.Matrix

/**
 * A property source that influence only a sub-part (zone) of the environment
 *
 * @tparam T type of property
 */
trait ZoneSource[T <: Property] extends PropertySource[T] with Zone {

  def filter: Matrix[T#Variation]

  val width: Int = filter cols

  val height: Int = filter rows
}

object ZoneSource {

  /** An implementation for a zone-source of a property who is applied to a center position */
  case class Source[T <: Property](
    filter: Matrix[T#Variation],
    x: Int, y: Int,
  ) extends ZoneSource[T]
}