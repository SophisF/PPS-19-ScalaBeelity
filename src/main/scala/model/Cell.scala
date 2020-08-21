package scala.model

import scala.model.Cell.limit
import scala.model.property.Property.{Humidity, Pressure, Property, Temperature}
import scala.model.property.{Property, PropertyVariation}

/**
 * Class that represent an environment cell
 *
 * @param temperature in the area represented by the cell
 * @param humidity in the area represented by the cell
 * @param pressure in the area represented by the cell
 *
 * @author Paolo Baldini
 */
case class Cell(temperature: Int, humidity: Int, pressure: Int) {

  /**
   * Method made for iterate over the properties
   *
   * @param property of which we want to know the value
   * @return the value of the property
   */
  def get(property: Property): Int = property match {
    case Temperature => temperature
    case Humidity => humidity
    case Pressure => pressure
  }

  /**
   * Variate a specific property of the cell summing the value specified by the variation
   *
   * @param variation contains value and target of the variation
   * @return the varied cell
   */
  def +(variation: PropertyVariation): Cell = variation.property match {
    case Temperature => Cell(limit(Temperature, temperature + variation.value), humidity, pressure)
    case Humidity => Cell(temperature, limit(Humidity, humidity + variation.value), pressure)
    case Pressure => Cell(temperature, humidity, limit(Pressure, pressure + variation.value))
  }

  /**
   * Make an optional sum. If the optional is empty, it returns the same cell;
   * otherwise exec the normal sum
   *
   * @param variation optionally contains value and target of the variation
   * @return the optionally varied cell
   */
  def +(variation: Option[PropertyVariation]): Cell = variation map(this + _) getOrElse this
}
object Cell {

  /**
   * Apply field-wise operation between two cell. Resulting value cannot exceed property limits/range
   *
   * @param first a cell
   * @param second a cell
   * @param property the operation between element of 'first' and 'second'
   * @return a cell on which every field is the (limited) result of the operation applied to between the value of the
   *        'first' and 'second' cells
   */
  def operation(first: Cell, second: Cell)(property: (Int, Int) => Int): Cell = Cell(
    limit(Temperature, property(first.temperature, second.temperature)),
    limit(Humidity, property(first.humidity, second.humidity)),
    limit(Pressure, property(first.pressure, second.pressure)))

  /**
   * Make the passed value respect the limit decreed by the property
   *
   * @param property of which use the range
   * @param value to set in range
   * @return ranged value
   */
  private def limit(property: Property, value: Int): Int = value match {
    case value if value >= 0 => Math.min(value, Property.range(property).maxValue)
    case value => Math.max(value, Property.range(property).minValue)
  }
}
