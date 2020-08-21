package scala.model.environment.property.realization

import scala.model.environment.property.PropertyHelper
import scala.model.environment.property.realization.TemperatureProperty.{TemperatureState, ValueType}

object TemperaturePropertyHelper {

  implicit def TemperatureHelper: PropertyHelper[TemperatureProperty] =
    (first: TemperatureProperty#State, second: TemperatureProperty#State) => first.value + second.value

  implicit def toValueType[T: Numeric](value: T): ValueType = implicitly[Numeric[T]].toInt(value)

  implicit def toState[T: Numeric](value: T): TemperatureState = TemperatureState(implicitly[Numeric[T]].toInt(value))

  implicit def percentage(value: Int, percent: Int): Int = value * percent / 100
}
