package scala.model.environment.property.realization

import scala.model.environment.time.Time

sealed trait PressureProperty extends IntegerProperty {
  trait PressureState extends IntegerState
  trait PressureVariation extends IntegerVariation
  trait PressureTimedVariation extends IntegerTimedVariation
}

object PressureProperty extends PressureProperty {
  val default: Int = 1000
  override val maxValue: Int = 1080
  override val minValue: Int = 980

  implicit def state(_value: Int): PressureState = new PressureState { override val value: Int = limit(_value) }

  implicit def variation(_value: Int): PressureVariation = new PressureVariation {
    val value: Int = _value
    override def vary[S <: State](_state: S): PressureState = state(_state.value + _value)
  }

  implicit def timedVariation(value: Int, start: Time, duration: Time): PressureTimedVariation =
    new PressureTimedVariation {
      override def instantaneous(instant: Time): PressureVariation = instantaneous(value, start, duration, instant)
    }

  override def seasonalTrend: PressureTimedVariation = (_: Time) => 0
}