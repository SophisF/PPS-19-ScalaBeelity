package scala.model.environment.property

import scala.model.Time

/**
 * Represents a property with a time-related behaviour.
 * Particularly, it can have variations whose values depends from the specific time instant
 */
trait TimedProperty extends Property {
  type TimedVariationType <: TimedVariation

  /**
   * Represents a variation of a state whose strategy
   * vary depending on the time of call
   */
  trait TimedVariation {
    def instantaneous(instant: Time): VariationType
  }
}