package scala.model.environment.property.realization.integer.utils

import breeze.linalg.DenseMatrix

import scala.model.Time
import scala.model.environment.property.realization.integer.TimedProperty
import scala.model.environment.property.utils.{TimedFilterGenerator => Utils}

trait TimedFilterGenerator extends Utils with FilterGenerator { this: TimedProperty =>

  override def timedFilter(width: Int, height: Int, duration: Time, start: Time): DenseMatrix[TimedVariationType] =
    filter(width, height)(zeroCenteredRange._1, zeroCenteredRange._2).map(d => timedVariation(d toInt, start, duration))
}
