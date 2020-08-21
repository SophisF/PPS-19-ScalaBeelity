package scala.model.environment.property.realization

import scala.model.environment.property.realization.TemperaturePropertyHelper.toState
import scala.model.environment.property.{Property, Range, Variation}
import scala.model.environment.time.{Time, TimeData}
import scala.model.environment.utility.IteratorHelper.RichIterator

sealed trait TemperatureProperty extends Property with Range with TimeData[Int] {
  override type ValueType = Int
}

object TemperatureProperty extends TemperatureProperty {
  private val MonthlyIncrement = 3

  override val default: Int = 0
  override val maxValue: Int = 50
  override val minValue: Int = -50

  // TODO se implicit limit funziona -> do nothing, altrimenti -> apply con limit
  case class TemperatureState(value: Int) extends TemperatureProperty.State

  implicit def operation: (TemperatureProperty#State, Variation[TemperatureProperty]) => TemperatureState =
    (state, variation) => state.value + variation.value

  implicit def instantValue(time: Time): Int = (0 until 6).iterator.mirror(false).map(_ * MonthlyIncrement)
    .drop(time.month).toSeq.head

  implicit def variation(older: Time, newer: Time): Int = instantValue(newer) - instantValue(older)
}