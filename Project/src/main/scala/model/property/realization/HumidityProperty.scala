package scala.model.property.realization

import scala.model.property.{Property, PropertyVariation, Range}

sealed trait HumidityProperty extends Property with Range {
  override type ValueType = Int
}

object HumidityProperty extends HumidityProperty {
  override val default: Int = 10
  override val maxValue: Int = 100
  override val minValue: Int = 0

  implicit def toValueType[T: Numeric](value: T): ValueType = implicitly[Numeric[T]].toInt(value)

  implicit def operation: (PropertyVariation[HumidityProperty], ValueType) => ValueType =
    (variation, value) => variation.value + value

  /*override implicit def instantValue(time: Int): Int = maxValue * Math.sin(time % 365) toInt

  override def variation(olderTime: Int, newerTime: Int): Int => Int =
    value => value + instantValue(olderTime) - instantValue(newerTime)*/
}