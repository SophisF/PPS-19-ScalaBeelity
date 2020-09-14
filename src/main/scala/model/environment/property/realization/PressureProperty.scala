package scala.model.environment.property.realization

import scala.model.environment.time.Time

sealed trait PressureProperty extends IntegerProperty {
  trait PressureState extends IntegerState
  trait PressureVariation extends Variation
  trait PressureTimedVariation extends IntegerTimedVariation
}

object PressureProperty extends PressureProperty {
  val default: Int = 1
  override val maxValue: Int = 5
  override val minValue: Int = -5

  implicit def state(_value: Int): PressureState = new PressureState { override val value: Int = limit(_value) }

  implicit def variation(_value: Int): PressureVariation = new PressureVariation {
    override def vary[S <: State](_state: S): PressureState = state(_state.value + _value)
  }

  implicit def timedVariation(value: Int, start: Time, duration: Time): PressureTimedVariation =
    new PressureTimedVariation {
      override def instantaneous(instant: Time): PressureVariation = instantaneous(value, start, duration, instant)
    }
}