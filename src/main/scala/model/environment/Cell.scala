package scala.model.environment

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
  temperature: TemperatureProperty.StateType = TemperatureProperty.default,
  humidity: HumidityProperty.StateType = HumidityProperty.default,
  pressure: PressureProperty.StateType = PressureProperty.default
) {

  def apply[T <: Property](property: PropertyValue[_]): T#StateType = (property match {
    case Temperature => temperature
    case Humidity => humidity
    case Pressure => pressure
  }).asInstanceOf[T#StateType]

  def +(variation: Property#Variation): Cell = variation match {
    case _ if variation isNull => this
    case v: TemperatureProperty.VariationType => Cell(v vary temperature, humidity, pressure)
    case v: HumidityProperty.VariationType => Cell(temperature, v vary humidity, pressure)
    case v: PressureProperty.VariationType => Cell(temperature, humidity, v vary pressure)
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

object Cell {

  def equals(first: Cell, second: Cell): Boolean = first.temperature == second.temperature &&
    first.humidity == second.humidity && first.pressure == second.pressure
}
