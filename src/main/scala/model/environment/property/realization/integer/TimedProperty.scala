package scala.model.environment.property.realization.integer

import scala.model.Time
import scala.model.environment.property.{TimedProperty => TimedProperty_}

/**
 * A property related to time who has a time-based behaviour
 */
trait TimedProperty extends TimedProperty_ with Property {
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
}
