package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.Time
import scala.model.environment.property.{FilterBuilder, TimeDependentProperty}
import scala.model.environment.utility.SequenceHelper.RichSequence

trait IntegerTimedProperty extends TimeDependentProperty {
  override type TimedVariationType = IntegerTimedVariation

  implicit def variation(value: Int): VariationType

  def timedVariation(value: Int, start: Time, duration: Time): TimedVariationType = new IntegerTimedVariation {
    private var evaluated: Double = 0

    override def instantaneous(instant: Time): VariationType = {
      val percentage = (instant - start) / duration.toDouble - evaluated
      if (percentage * value > 0) evaluated += percentage
      variation(percentage * value toInt)
    }
  }

  trait IntegerTimedVariation extends TimedVariation

  def maxValue: Int
  def minValue: Int

  override def timedFilter(xDecrement: Int, yDecrement: Int, start: Time, duration: Time)
  : DenseMatrix[TimedVariationType] = FilterBuilder.gaussianFunction3d((minValue to maxValue)
    .filter(_ != 0).random().get, 0, xDecrement, yDecrement).map(d => timedVariation(d.toInt, start, duration))

  override def seasonalTrend: TimedVariationType = (_: Time) => 0
}
