package scala.model.environment.property

import breeze.linalg.DenseMatrix

import scala.model.environment.time.FiniteData

/**
 * Define utilities methods that are not part of the definition of Property.
 *
 * @tparam T property to help
 *
 * @author Paolo Baldini
 */
trait PropertyHelper[T <: Property] {

  def generateInstantaneousFilter(width: Int, height: Int): DenseMatrix[T#Variation]

  def percentage(state: T#State): Double
}