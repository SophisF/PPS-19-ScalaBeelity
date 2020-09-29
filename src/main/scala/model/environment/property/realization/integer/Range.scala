package scala.model.environment.property.realization.integer

import scala.model.environment.property.{Range => Range_}

/**
 * Represent a generic range who works with data of type Int.
 * Simplify the use (and help DRY) of numeric ranges
 */
trait Range extends Range_ {
  override type ValueType = Int

  /**
   * Return the with of the range (maxValue - minValue)
   *
   * @return the with of the range
   */
  def rangeWidth: Int = maxValue - minValue

  /**
   * Get the middle value of the range
   *
   * @return the center value of the range
   */
  def rangeCenter: Int = minValue + rangeWidth / 2

  /**
   * Get a range where the center value is remapped to the zero value
   *
   * @return a range of the same width where the center value is zero
   */
  def zeroCenteredRange: (Int, Int) = (minValue - rangeCenter, maxValue - rangeCenter)
}