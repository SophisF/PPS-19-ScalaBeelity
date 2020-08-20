package scala.model.environment.property.realization

import scala.model.environment.property.realization.TemperaturePropertyHelper.toState

import scala.model.environment.property.{Property, Range, Variation}
import scala.model.environment.time.{Time, TimeData}

sealed trait TemperatureProperty extends Property with Range with TimeData[Int] {
  override type ValueType = Int
}

object TemperatureProperty extends TemperatureProperty {
  override val default: Int = 0
  override val maxValue: Int = 50
  override val minValue: Int = -50

  // TODO se implicit limit funziona -> do nothing, altrimenti -> apply con limit
  case class TemperatureState(value: Int) extends TemperatureProperty.State

  implicit def operation: (TemperatureProperty#State, Variation[TemperatureProperty]) => TemperatureState =
    (state, variation) => state.value + variation.value

  // TODO periodic property source
  implicit def instantValue(time: Int): Int = (0 until 12).map(x =>  // TODO magic number
    if (x < 6) x * 3
    else (12 - x) * 3
  ).drop((time / 30) % 12).head

  implicit def variation(olderTime: Int, newerTime: Int): Int = instantValue(Time.time) - instantValue(olderTime)
}