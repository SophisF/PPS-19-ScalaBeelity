package scala.model.environment

import scala.collection.immutable.HashMap
import scala.model.environment.property.PropertyType.{Humidity, Pressure, Temperature}
import scala.model.environment.property.PropertyVariation.vary
import scala.model.environment.property.realization.{HumidityProperty, PressureProperty, TemperatureProperty}
import scala.model.environment.property.{Property, PropertyType, PropertyVariation}

/**
 * Class that represent an environment cell
 *
 * @param temperature in the area represented by the cell
 * @param humidity in the area represented by the cell
 * @param pressure in the area represented by the cell
 *
 * @author Paolo Baldini
 */
class Cell(temperature: Int, humidity: Int, pressure: Int) {
  private val map = HashMap((Temperature, temperature), (Humidity, humidity), (Pressure, pressure))

  /**
   * Used to iterate over the properties
   *
   * @param property of which we want to know the value
   * @return the value of the property
   */
  def apply(property: PropertyType.Value): Int = map(property)

  def +[T <: Property](variation: PropertyVariation[T]): Cell = variation match {
    case v:PropertyVariation[TemperatureProperty] => Cell(vary[TemperatureProperty](temperature, v), humidity, pressure)
    case v:PropertyVariation[HumidityProperty] => Cell(vary[HumidityProperty](temperature, v), humidity, pressure)
    case v:PropertyVariation[PressureProperty] => Cell(vary[PressureProperty](temperature, v), humidity, pressure)
    case _ => this
  }

  /**
   * Make an optional sum. If the optional is empty, it returns the same cell;
   * otherwise exec the normal sum
   *
   * @param variation optionally contains value and target of the variation
   * @return the optionally varied cell
   */
  def +?[T <: Property](variation: Option[PropertyVariation[T]]): Cell = variation map (this + _) getOrElse this
}
object Cell {

  def apply(temperature: Int, humidity: Int, pressure: Int): Cell = new Cell(temperature, humidity, pressure)

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
    TemperatureProperty.limit(property(first(Temperature), second(Temperature))),
    HumidityProperty.limit(property(first(Humidity), second(Humidity))),
    PressureProperty.limit(property(first(Pressure), second(Pressure))))
}
