package scala.model.environment.property.realization

import scala.model.environment.property.{Property, Range, Variation, realization}

sealed trait HumidityProperty extends Property with Range {
  override type ValueType = Int
}

object HumidityProperty extends HumidityProperty {
  override val default: Int = 10
  override val maxValue: Int = 100
  override val minValue: Int = 0

  // TODO se implicit limit funziona -> do nothing, altrimenti -> apply con limit
  case class HumidityState(value: Int) extends HumidityProperty.State

  implicit def operation: (HumidityProperty#State, Variation[HumidityProperty]) => HumidityState =
    (state, variation) => HumidityState(state.value + variation.value)

  /*override implicit def instantValue(time: Int): Int = maxValue * Math.sin(time % 365) toInt

  override def variation(olderTime: Int, newerTime: Int): Int => Int =
    value => value + instantValue(olderTime) - instantValue(newerTime)*/
}