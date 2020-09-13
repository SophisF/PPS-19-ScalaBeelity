package scala.model.environment.property.realization

sealed trait PressureProperty extends RangedIntegerProperty {
  trait PressureState extends RangedIntegerState
  trait PressureVariation extends Variation
}

object PressureProperty extends PressureProperty {
  val default: Int = 1
  override val maxValue: Int = 5
  override val minValue: Int = -5

  implicit def state(_value: Int): PressureState = new PressureState { override val value: Int = limit(_value) }

  implicit def variation(_value: Int): PressureVariation = new PressureVariation {
    override def vary[S <: State](_state: S): PressureState = state(_state.value + _value)
  }
}