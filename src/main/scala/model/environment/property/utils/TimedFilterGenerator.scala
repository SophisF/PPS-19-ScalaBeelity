package scala.model.environment.property.utils

import breeze.linalg.DenseMatrix

import scala.model.Time
import scala.model.Time.now
import scala.model.environment.property.TimedProperty

/**
 * Adds utility to generate a timed-filter
 */
private[environment] trait TimedFilterGenerator extends FilterGenerator { this: TimedProperty =>

  /**
   * Generate a filter of time-related variations
   *
   * @param width influence the width of the filter
   * @param height influence the height of the filter
   * @param duration the expected life-duration of the filter (within which to be fully applied)
   * @param start the time from which the filter should start to be active
   * @return a filter of timed-variations
   */
  def timedFilter(width: Int, height: Int, duration: Time, start: Time = now()): DenseMatrix[TimedVariationType]
}
