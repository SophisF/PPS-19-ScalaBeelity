package scala.model.environment.property

import breeze.linalg.DenseMatrix

import scala.model.environment.time.Time

trait TimeDependentProperty extends Property {
  type TimedVariationType <: TimedVariation

  trait TimedVariation {
    def instantaneous(instant: Time): VariationType
  }

  def seasonalTrend: TimedVariationType

  def timedFilter(xDecrement: Int, yDecrement: Int, start: Time, duration: Time): DenseMatrix[TimedVariationType]
}