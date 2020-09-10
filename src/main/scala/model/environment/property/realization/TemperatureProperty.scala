package scala.model.environment.property.realization

import scala.model.environment.property.Range
import scala.model.environment.time.TimeData

sealed trait TemperatureProperty extends IntegerProperty with Range with TimeData[Int] {
  trait TemperatureState extends State
  trait TemperatureVariation extends Variation
}

object TemperatureProperty extends TemperatureProperty {
  private val MonthlyIncrement = 3

  override val default: Int = 0
  override val maxValue: Int = 50
  override val minValue: Int = -50

  implicit def state(_value: Int): TemperatureState = new TemperatureState { override val value: Int = _value }

  implicit def variation(_value: Int): TemperatureVariation = new TemperatureVariation {
    override def vary[S <: State](_state: S): TemperatureState = state(_state.value + _value)
  }

  // TODO se implicit limit funziona -> do nothing, altrimenti -> apply con limit
  //case class TemperatureState(override val value: Int = default) extends State

  /*case class Variation(value: Int) extends TemperatureVariation {
    override def vary[S <: State](state: S): TemperatureState = TemperatureState()
  }*/

  /*
  implicit def instantValue(time: Time): Int = (0 until 6).iterator.mirror(false).map(_ * MonthlyIncrement)
    .drop(time.month).toSeq.head

  implicit def variation(older: Time, newer: Time): Int = instantValue(newer) - instantValue(older)*/
}