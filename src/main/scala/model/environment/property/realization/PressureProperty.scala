package scala.model.environment.property.realization

import scala.model.environment.property.Range

sealed trait PressureProperty extends IntegerProperty with Range {
  trait PressureState extends State
  trait PressureVariation extends Variation
}

object PressureProperty extends PressureProperty {
  override val default: Int = 1
  override val maxValue: Int = 5
  override val minValue: Int = -5

  implicit def state(_value: Int): PressureState = new PressureState { override val value: Int = limit(_value) }

  implicit def variation(_value: Int): PressureVariation = new PressureVariation {
    override def vary[S <: State](_state: S): PressureState = state(_state.value + _value)
  }

  // TODO se implicit limit funziona -> do nothing, altrimenti -> apply con limit
  //case class PressureState(override val value: Int = default) extends State

  /*case class Variation(value: Int) extends PressureVariation {
    override def vary[S <: State](state: S): PressureState = PressureState(state.value + value)
  }*/
}