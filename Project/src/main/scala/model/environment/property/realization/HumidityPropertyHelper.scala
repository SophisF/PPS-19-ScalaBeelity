package scala.model.environment.property.realization

import scala.model.environment.property.PropertyHelper
import scala.model.environment.property.realization.HumidityProperty.{HumidityState, ValueType}

object HumidityPropertyHelper {

  implicit def HumidityHelper: PropertyHelper[HumidityProperty] =
    (first: HumidityProperty#State, second: HumidityProperty#State) => first.value + second.value

  implicit def toValueType[T: Numeric](value: T): ValueType = implicitly[Numeric[T]].toInt(value)

  implicit def toState[T: Numeric](value: T): HumidityState = HumidityState(implicitly[Numeric[T]].toInt(value))
}