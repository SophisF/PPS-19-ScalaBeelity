package scala.model.environment.property

import breeze.linalg.DenseMatrix

import scala.model.Time
import scala.model.Time.now

/**
 * Represents a property with a time-related behaviour.
 * Particularly, it can have variations whose values depends from the specific time instant
 */
trait TimeDependentProperty extends Property {
  type TimedVariationType <: TimedVariation

  trait TimedVariation {
    def instantaneous(instant: Time): VariationType
  }

  def timedFilter(width: Int, height: Int, duration: Time, start: Time = now()): DenseMatrix[TimedVariationType]
}