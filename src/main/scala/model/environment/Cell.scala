package scala.model.environment

import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType.{Humidity, PropertyValue, Temperature}
import scala.model.environment.property.realization.{HumidityProperty, PressureProperty, TemperatureProperty}

/**
 * Class that represent an environment cell
 *
 * @param temperature in the area represented by the cell
 * @param humidity in the area represented by the cell
 * @param pressure in the area represented by the cell
 */
private[environment] case class Cell(
  temperature: TemperatureProperty.StateType = TemperatureProperty.default,
  humidity: HumidityProperty.StateType = HumidityProperty.default,
  pressure: PressureProperty.StateType = PressureProperty.default
) {

  /**
   * Returns the state of the specified property
   *
   * @param property type of the property to get
   * @tparam T, type to property
   * @return the state of the property
   */
  def apply[T <: Property](property: PropertyValue[_]): T#StateType = (property match {
    case Temperature => temperature
    case Humidity => humidity
    case _ => pressure
  }).asInstanceOf[T#StateType]

  /**
   * Vary a cell given the specified variation
   *
   * @param variation of property
   * @return a varied cell
   */
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

private[environment] object Cell {

  def equals(first: Cell, second: Cell): Boolean = first.temperature == second.temperature &&
    first.humidity == second.humidity && first.pressure == second.pressure
}
