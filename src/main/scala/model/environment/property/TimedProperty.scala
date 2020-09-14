package scala.model.environment.property

import breeze.linalg.DenseMatrix

import scala.model.environment.time.Time

trait TimedProperty extends Property {

  trait TimedVariation {
    def instantaneous(instant: Time): Variation
  }

  def generateTimedFilter(xDecrement: Int, yDecrement: Int, start: Time, duration: Time): DenseMatrix[TimedVariation]
}