package scala.model.environment.property.typed.int

import breeze.linalg.DenseMatrix

import scala.model.Time
import scala.model.environment.property.TimeDependentProperty
import scala.model.environment.property.typed.int.IntProperty.filter

/**
 * A property related to time who has a time-based behaviour
 */
trait IntTimedProperty extends TimeDependentProperty with IntRange {
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

  /** A partial implementation of a timed-variation for a timed-integer-type property */
  trait IntegerTimedVariation extends TimedVariation

  override def timedFilter(width: Int, height: Int, duration: Time, start: Time): DenseMatrix[TimedVariationType] =
    filter(width, height)(zeroCenteredRange._1, zeroCenteredRange._2).map(d => timedVariation(d toInt, start, duration))
}
