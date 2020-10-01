package scala.model.environment.property.realization.integer

import scala.model.Time
import scala.model.environment.property.{TimedProperty => TimedProperty_}

/**
 * A property related to time who has a time-based behaviour
 */
private[realization] trait TimedProperty extends TimedProperty_ with Property {
  override type TimedVariationType = IntegerTimedVariation

  /**
   * Represents a variation of a state whose strategy
   * vary depending on the time of call
   */
  def timedVariation(value: Int, start: Time, duration: Time): TimedVariationType = new IntegerTimedVariation {
    private val minimum: Double = 1.0
    private var evaluated: Int = 0

    /**
     * Returns the variation at the specified instant
     *
     * @param instant of which calculate the variation
     * @return the variation to use in this instant
     */
    override def instantaneous(instant: Time): VariationType = {
      val percentage = ((instant - start) / duration.toDouble) min minimum
      val variationValue = (percentage * value - evaluated).toInt
      if (variationValue.abs >= 1) evaluated += variationValue
      variation(variationValue)
    }
  }

  /** A partial implementation of a timed-variation for a timed-integer-type property */
  trait IntegerTimedVariation extends TimedVariation
}
