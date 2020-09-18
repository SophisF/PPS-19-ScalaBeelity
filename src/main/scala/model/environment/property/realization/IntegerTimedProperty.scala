package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.Time
import scala.model.environment.property.TimeDependentProperty
import scala.model.environment.property.realization.IntegerProperty.filter

trait IntegerTimedProperty extends TimeDependentProperty {
  override type TimedVariationType = IntegerTimedVariation

  implicit def variation(value: Int): VariationType

  def timedVariation(value: Int, start: Time, duration: Time): TimedVariationType = new IntegerTimedVariation {
    private var evaluated: Int = 0

    override def instantaneous(instant: Time): VariationType = {
      val percentage = ((instant - start) / duration.toDouble).min(1.0)
      val variationValue = (percentage * value - evaluated).toInt
      if (variationValue.abs >= 1) evaluated += variationValue
      variation(variationValue)
    }
  }

  trait IntegerTimedVariation extends TimedVariation

  def maxValue: Int
  def minValue: Int

  override def timedFilter(width: Int, height: Int, duration: Time, start: Time): DenseMatrix[TimedVariationType] =
    filter(width, height)(minValue, maxValue).map(d => timedVariation(d toInt, start, duration))

  override def seasonalTrend: TimedVariationType = (_: Time) => 0
}
