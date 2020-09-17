package scala.model.environment.property.realization

sealed trait PressureProperty extends IntegerProperty with IntegerTimedProperty

object PressureProperty extends PressureProperty {
  override val default: Int = 1000
  override val maxValue: Int = 1080
  override val minValue: Int = 980
}