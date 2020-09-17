package scala.model.environment.property

import breeze.linalg.DenseMatrix

import scala.model.Time

trait TimeDependentProperty extends Property {
  type TimedVariationType <: TimedVariation

  trait TimedVariation {
    def instantaneous(instant: Time): VariationType
  }

  def seasonalTrend: TimedVariationType

  def timedFilter(width: Int, height: Int, duration: Time, start: Time = Time.now()): DenseMatrix[TimedVariationType]
}