package scala.model.environment.property.source

import scala.model.Time
import scala.model.Time.now
import scala.model.environment.property.TimedProperty
import scala.model.environment.time.Timed
import scala.utility.DenseMatrixHelper.Matrix

/**
 * A zone-related source who has a time behaviour.
 * In fact, it changes its filter value based on time.
 *
 * @param x center of the filter in X axis
 * @param y center of the filter in Y axis
 * @param daysDuration lifetime of the filter
 * @param fireTime time at which the filter began to work
 * @param finalFilter the filter that should have been applied at the source death
 * @tparam T type of time-dependent-property
 */
private[environment] case class ContinuousSource[T <: TimedProperty](
  x: Int, y: Int,
  daysDuration: Int,
  fireTime: Time = now()
)(
  finalFilter: Matrix[T#TimedVariation]
) extends ZoneSource[T] with Timed {

  /**
   * Filter now.
   *
   * @return matrix of variation
   */
  def filter: Matrix[T#Variation] = filter(now())

  /**
   * Filter at time.
   *
   * @param time for apply variation
   * @return matrix of variation
   */
  def filter(time: Time): Matrix[T#Variation] = finalFilter map[T#Variation, Matrix[T#Variation]](_ instantaneous time)
}