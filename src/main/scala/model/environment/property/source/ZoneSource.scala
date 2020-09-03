package scala.model.environment.property.source

import breeze.linalg.DenseMatrix

import scala.model.environment.matrix.Zone
import scala.model.environment.property.{Property, Variation}
import scala.model.environment.property.Variation.GenericVariation

/**
 * A property source that influence only a sub-part of the environment
 *
 * @tparam T type of property
 *
 * @author Paolo Baldini
 */
trait ZoneSource[T <: Property] extends PropertySource[T] with Zone {
  def filter: DenseMatrix[GenericVariation[T]]
}
