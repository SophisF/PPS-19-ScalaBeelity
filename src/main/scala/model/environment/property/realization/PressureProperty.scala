package scala.model.environment.property.realization

import scala.model.environment.property.{Property, Range, Variation}

sealed trait PressureProperty extends Property with Range {
  override type ValueType = Int
}

object PressureProperty extends PressureProperty {
  override val default: Int = 1
  override val maxValue: Int = 5
  override val minValue: Int = -5

  // TODO se implicit limit funziona -> do nothing, altrimenti -> apply con limit
  case class PressureState(value: Int) extends PressureProperty.State {
    override def asNumericPercentage(): Int = (value - minValue) * 100 / (maxValue - minValue)
  }

  implicit def operation: (PressureProperty#State, Variation[PressureProperty]) => PressureState =
    (state, variation) => PressureState(state.value + variation.value)
}