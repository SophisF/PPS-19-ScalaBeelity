package scala.model.environment.property.realization

import scala.model.environment.property.{Property, Variation, Range}

sealed trait PressureProperty extends Property with Range {
  override type ValueType = Int
}

object PressureProperty extends PressureProperty {
  override val default: Int = 1
  override val maxValue: Int = 5
  override val minValue: Int = -5

  implicit def toValueType[T: Numeric](value: T): ValueType = implicitly[Numeric[T]].toInt(value)

  implicit def operation: (Variation[PressureProperty], ValueType) => ValueType =
    (variation, value) => variation.value + value
}