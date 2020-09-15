package scala.model.environment.property

import breeze.linalg.DenseMatrix

import scala.model.environment.time.Time

trait TimeDependentProperty extends Property {

  trait TimedVariation {
    def instantaneous(instant: Time): Variation
  }

  def seasonalTrend: TimedVariation

  def timedFilter(xDecrement: Int, yDecrement: Int, start: Time, duration: Time): DenseMatrix[TimedVariation]
}