package scala.model.environment.property

import breeze.linalg.DenseMatrix

import scala.model.environment.property.Variation.GenericVariation

/**
 * Define utilities methods that are not part of the definition of Property.
 *
 * @tparam T property to help
 *
 * @author Paolo Baldini
 */
trait PropertyHelper[T <: Property] {

  def generateFilter(width: Int, height: Int): DenseMatrix[GenericVariation[T]]
}