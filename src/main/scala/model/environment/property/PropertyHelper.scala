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

  def generateContinuousFilter(width: Int, height: Int, duration: Int): DenseMatrix[Int => Option[T#Variation]]

  def generateFiniteFilter(width: Int, height: Int, duration: Int): DenseMatrix[FiniteData[T#Variation]]
}