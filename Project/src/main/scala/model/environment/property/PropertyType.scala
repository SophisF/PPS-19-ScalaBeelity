package scala.model.environment.property

import scala.model.environment.property.realization.{HumidityProperty, PressureProperty, TemperatureProperty}

object PropertyType extends Enumeration {

  case class PropertyTypeValue(property: Property) extends Val()

  val Temperature: Value = PropertyTypeValue(TemperatureProperty)
  val Humidity: Value = PropertyTypeValue(HumidityProperty)
  val Pressure: Value = PropertyTypeValue(PressureProperty)
}