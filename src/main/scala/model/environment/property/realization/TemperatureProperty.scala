package scala.model.environment.property.realization

import scala.model.environment.time.TimeData

sealed trait TemperatureProperty extends RangedIntegerProperty with TimeData[Int] {
  trait TemperatureState extends RangedIntegerState
  trait TemperatureVariation extends Variation
}

object TemperatureProperty extends TemperatureProperty {
  private val MonthlyIncrement = 3

  val default: Int = 0
  override val maxValue: Int = 50
  override val minValue: Int = -50

  implicit def state(_value: Int): TemperatureState = new TemperatureState { override val value: Int = limit(_value) }

  implicit def variation(_value: Int): TemperatureVariation = new TemperatureVariation {
    override def vary[S <: State](_state: S): TemperatureState = state(_state.value + _value)
  }
  /*
  implicit def instantValue(time: Time): Int = (0 until 6).iterator.mirror(false).map(_ * MonthlyIncrement)
    .drop(time.month).toSeq.head

  implicit def variation(older: Time, newer: Time): Int = instantValue(newer) - instantValue(older)*/
}