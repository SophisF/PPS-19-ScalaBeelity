package scala.model.environment.property.realization

import scala.model.environment.property.Range

sealed trait PressureProperty extends IntegerProperty with Range

object PressureProperty extends PressureProperty {
  override val default: Int = 1
  override val maxValue: Int = 5
  override val minValue: Int = -5

  // TODO se implicit limit funziona -> do nothing, altrimenti -> apply con limit
  case class PressureState(value: Int) extends IntegerState {
    override implicit def apply(value: Int): PressureState = PressureState(value)
  }
}