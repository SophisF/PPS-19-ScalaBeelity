package scala.model.environment.property

import scala.model.Time

/**
 * Represents a property with a time-related behaviour.
 * Particularly, it can have variations whose values depends from the specific time instant
 */
private[environment] trait TimedProperty extends Property {

  /**
   * Contains the strategy to build a variation from a specific time-instant
   */
  type TimedVariationType <: TimedVariation

  /**
   * Represents a variation of a state whose strategy
   * vary depending on the time of call
   */
  trait TimedVariation {

    /**
     * Returns the variation at the specified instant
     *
     * @param instant of which calculate the variation
     * @return the variation to use in this instant
     */
    def instantaneous(instant: Time): VariationType
  }
}