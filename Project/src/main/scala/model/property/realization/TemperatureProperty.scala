package scala.model.property.realization

import scala.model.property.{Property, PropertyVariation, Range}
import scala.model.time.{Time, TimeData}

sealed trait TemperatureProperty extends Property with Range with TimeData[Int] {
  override type ValueType = Int
}

object TemperatureProperty extends TemperatureProperty {
  override val default: Int = 0
  override val maxValue: Int = 50
  override val minValue: Int = -50

  implicit def toValueType[T: Numeric](value: T): ValueType = implicitly[Numeric[T]].toInt(value)

  implicit def operation: (PropertyVariation[TemperatureProperty], ValueType) => ValueType =
    (variation, value) => variation.value + value

  // TODO periodic property source
  implicit def instantValue(time: Int): Int = (0 until 12).map(x =>  // TODO magic number
    if (x < 6) x * 3
    else (12 - x) * 3
  ).drop((time / 30) % 12).head

  implicit def variation(olderTime: Int, newerTime: Int): Int = instantValue(Time.time) - instantValue(olderTime)

  /*override implicit def instantValue(time: Int): Int = (0 until 12).map(x =>  // TODO magic number
    if (x < 6) x * 3
    else (12 - x) * 3
  ).drop((time / 30) % 12).head

  override def variation(olderTime: Int, newerTime: Int): Int => Int =
    value => value + instantValue(olderTime) - instantValue(newerTime)*/
}