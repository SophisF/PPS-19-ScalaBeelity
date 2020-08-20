package scala.model.environment.property.realization

import scala.model.environment.property.PropertyHelper
import scala.model.environment.property.realization.PressureProperty.{PressureState, ValueType}

object PressurePropertyHelper {

  implicit def PressureHelper: PropertyHelper[PressureProperty] =
    (first: PressureProperty#State, second: PressureProperty#State) => first.value + second.value

  implicit def toValueType[T: Numeric](value: T): ValueType = implicitly[Numeric[T]].toInt(value)

  implicit def toState[T: Numeric](value: T): PressureState = PressureState(implicitly[Numeric[T]].toInt(value))
}