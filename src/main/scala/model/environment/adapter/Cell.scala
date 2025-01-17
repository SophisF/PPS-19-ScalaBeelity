package scala.model.environment.adapter

import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType.PropertyValue
import scala.model.environment.{Cell => OldCell}

/**
 * An object structure to share between environment and outside world.
 * Help to maintain modularity
 */
private[model] trait Cell {
  def temperature(percentage: Boolean = false): Int = 0
  def humidity(percentage: Boolean = false): Int = 0
  def pressure(percentage: Boolean = false): Int = 0

  /**
   * Returns the value of the specified property
   *
   * @param property of which get the value
   * @param percentage if true, require the value to be in percentage
   * @return the numeric value of the property
   */
  def apply(property: PropertyValue[Property], percentage: Boolean = false): Int
}

private[model] object Cell {

  /**
   * Convert a private-type cell to this one
   *
   * @param cell the private cell
   * @return this cell
   */
  implicit def cellFromPrivateOne(cell: OldCell = OldCell()): Cell = new Cell {
    override def temperature(inPercentage: Boolean): Int = cell.temperature numericRepresentation inPercentage
    override def humidity(inPercentage: Boolean): Int = cell.humidity numericRepresentation inPercentage
    override def pressure(inPercentage: Boolean): Int = cell.pressure numericRepresentation inPercentage

    def apply(property: PropertyValue[Property], inPercentage: Boolean): Int =
      cell(property) numericRepresentation inPercentage
  }

  implicit def apply(
    temperature: Int = 0,
    humidity: Int = 0,
    pressure: Int = 0
  ): Cell = OldCell(temperature, humidity, pressure)
}
