package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.Time
import scala.model.environment.property.TimeDependentProperty

trait IntegerTimedProperty extends TimeDependentProperty {
  override type TimedVariationType = IntegerTimedVariation

  implicit def variation(value: Int): VariationType

  def timedVariation(value: Int, start: Time, duration: Time): TimedVariationType = new IntegerTimedVariation {
    private var evaluated: Double = 0

    override def instantaneous(instant: Time): VariationType = {
      val percentage = (instant - start) / duration.toDouble - evaluated
      if (percentage * value > 0) evaluated += percentage
      variation((percentage * value) toInt)
    }
  }

  trait IntegerTimedVariation extends TimedVariation

  def maxValue: Int
  def minValue: Int

  override def timedFilter(width: Int, height: Int, duration: Time, start: Time): DenseMatrix[TimedVariationType] =
    IntegerProperty.filter(width, height)(minValue, maxValue).map(d => timedVariation(d.toInt, start, duration))

  override def seasonalTrend: TimedVariationType = (_: Time) => 0
}
