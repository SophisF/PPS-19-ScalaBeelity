package scala.model.environment.property.realization.integer.utils

import breeze.linalg.DenseMatrix

import scala.model.Time
import scala.model.environment.property.realization.integer.TimedProperty
import scala.model.environment.property.utils.{TimedFilterGenerator => Utils}

/**
 * A timed-filter-generator who works with timed-properties with value-type Int
 */
private[realization] trait TimedFilterGenerator extends Utils with FilterGenerator { this: TimedProperty =>

  /**
   * Generate a filter of time-related variations
   *
   * @param width influence the width of the filter
   * @param height influence the height of the filter
   * @param duration the expected life-duration of the filter (within which to be fully applied)
   * @param start the time from which the filter should start to be active
   * @return a filter of timed-variations
   */
  override def timedFilter(width: Int, height: Int, duration: Time, start: Time): DenseMatrix[TimedVariationType] =
    filter(width, height)(zeroCenteredRange._1, zeroCenteredRange._2).map(d => timedVariation(d toInt, start, duration))
}
