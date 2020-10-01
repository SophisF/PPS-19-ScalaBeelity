package scala.model.environment.property.realization.integer.utils

import breeze.linalg.DenseMatrix

import scala.model.environment.property.GaussianFilterBuilder.function3d
import scala.model.environment.property.realization.integer.Property
import scala.model.environment.property.utils.{FilterGenerator => Utils}
import scala.utility.IterableHelper.RichIterable
import scala.utility.MathHelper.intValueOf

/**
 * A filter-generator who works with properties with value-type Int
 */
private[realization] trait FilterGenerator extends Utils { this: Property =>
  private val stop: Int = 0

  /**
   * Generates a simple filter of values
   *
   * @param xDecrement influences the width of the filter
   * @param yDecrement influences the height of the filter
   * @param minValue min value in the filter axes
   * @param maxValue max value in the filter axes
   * @return a matrix that represents a filter of double
   */
  def filter(xDecrement: Int, yDecrement: Int)(implicit minValue: Int, maxValue: Int): DenseMatrix[Double] =
    function3d((minValue to maxValue).filter(_ != 0).random.get, stop, xDecrement, yDecrement)

  /**
   * Generates a filter of variations
   *
   * @param xDecrement influence the width of the filter
   * @param yDecrement influence the height of the filter
   *  @return the filter of variations
   */
  override def generateFilter(xDecrement: Int, yDecrement: Int): DenseMatrix[VariationType] =
    filter(xDecrement, yDecrement)(zeroCenteredRange._1, zeroCenteredRange._2).map(variation(_))
}
