package scala.model.environment.property.utils

import breeze.linalg.DenseMatrix

import scala.model.Time
import scala.model.Time.now
import scala.model.environment.property.TimedProperty

trait TimedFilterGenerator extends FilterGenerator { this: TimedProperty =>

  def timedFilter(width: Int, height: Int, duration: Time, start: Time = now()): DenseMatrix[TimedVariationType]
}
