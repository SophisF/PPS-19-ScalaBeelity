package scala.model.environment

import scala.model.environment.property.realization.HumidityProperty.HumidityVariation
import scala.model.environment.property.realization.PressureProperty.PressureVariation
import scala.model.environment.property.realization.TemperatureProperty.TemperatureVariation
import scala.model.environment.property.realization.{HumidityProperty, PressureProperty, TemperatureProperty}
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType.PropertyValue
import scala.model.environment.property.PropertyType.{Humidity, Pressure, Temperature}

/**
 * Class that represent an environment cell
 *
 * @param temperature in the area represented by the cell
 * @param humidity in the area represented by the cell
 * @param pressure in the area represented by the cell
 *
 * @author Paolo Baldini
 */
case class Cell(
  temperature: TemperatureProperty.State = TemperatureProperty.default,
  humidity: HumidityProperty.State = HumidityProperty.default,
  pressure: PressureProperty.State = PressureProperty.default
) {

  def apply(property: PropertyValue[_]): Property#State = property match {
    case Temperature => temperature
    case Humidity => humidity
    case Pressure => pressure
  }

  def +(variation: Property#Variation): Cell = variation match {
    case v: TemperatureVariation => Cell(v vary temperature, humidity, pressure)
    case v: HumidityVariation => Cell(temperature, v vary humidity, pressure)
    case v: PressureVariation => Cell(temperature, humidity, v vary pressure)
    case _ => this
  }

  /**
   * Make an optional sum. If the optional is empty, it returns the same cell;
   * otherwise exec the normal sum
   *
   * @param variation optionally contains value and target of the variation
   * @return the optionally varied cell
   */
  def +?(variation: Option[Property#Variation]): Cell = variation map (this + _) getOrElse this
}
