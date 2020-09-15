package scala.model.environment.property.source

import breeze.linalg.DenseMatrix

import scala.model.environment.property.TimeDependentProperty
import scala.model.environment.time.{Time, Timed}

class ContinuousSource[T <: TimeDependentProperty](
  private val completeFilter: DenseMatrix[T#TimedVariation],
  val x: Int, val y: Int,
  val daysDuration: Int,
  val fireTime: Time = Time.now()
) extends ZoneSource[T] with Timed {

  val width: Int = filter cols
  val height: Int = filter rows

  override def filter: DenseMatrix[T#Variation] = completeFilter mapValues(_ instantaneous Time.now())
}