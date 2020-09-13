package scala.model.environment.property.realization

import scala.model.environment.property.Range

sealed trait HumidityProperty extends IntegerProperty with Range {
  trait HumidityState extends State
  trait HumidityVariation extends Variation
}

object HumidityProperty extends HumidityProperty {
  override val default: Int = 10
  override val maxValue: Int = 100
  override val minValue: Int = 0

  implicit def state(_value: Int): HumidityState = new HumidityState { override val value: Int = limit(_value) }

  implicit def variation(_value: Int): HumidityVariation = new HumidityVariation {
    override def vary[S <: State](_state: S): HumidityState = state(_state.value + _value)
  }
  /*
  override implicit def instantValue(time: Int): Int = maxValue * Math.sin(time % 365) toInt

  override def variation(olderTime: Int, newerTime: Int): Int => Int =
    value => value + instantValue(olderTime) - instantValue(newerTime)*/
}