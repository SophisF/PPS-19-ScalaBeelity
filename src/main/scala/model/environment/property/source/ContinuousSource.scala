package scala.model.environment.property.source

import breeze.linalg.DenseMatrix

import scala.model.Time
import scala.model.Time.now
import scala.model.environment.property.TimeDependentProperty
import scala.model.environment.time.Timed

/**
 * A zone-related source who has a time behaviour.
 * In fact, it changes its filter value based on time.
 *
 * @param completeFilter the filter that should have been applied at the source death
 * @param x center of the filter in X axis
 * @param y center of the filter in Y axis
 * @param daysDuration lifetime of the filter
 * @param fireTime time at which the filter began to work
 * @tparam T type of time-dependent-property
 */
class ContinuousSource[T <: TimeDependentProperty](
  val completeFilter: DenseMatrix[T#TimedVariation],
  val x: Int, val y: Int,
  val daysDuration: Int,
  val fireTime: Time = Time.now()
) extends ZoneSource[T] with Timed {

  val width: Int = filter cols
  val height: Int = filter rows

  override def filter: DenseMatrix[T#Variation] = completeFilter map(_.instantaneous(now()).asInstanceOf[T#Variation])
}