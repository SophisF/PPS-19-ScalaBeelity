package scala.model

import scala.model.property.Filter._
import scala.model.property.Property
import scala.model.property.Property._

/**
 * Class that represent an environment cell
 * TODO study how to develop it
 *
 * @param temperature of the cell
 * @param humidity of the cell
 * @param pressure of the cell
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
    case Property.Temperature => temperature
    case Property.Humidity => humidity
    case Property.Pressure => pressure
  }

  /**
   * Variate a specific property of the cell summing the value specified by the variation
   *
   * @param variation contains value and target of the variation
   * @return the varied cell
   */
  def +(variation: Variation[Int]): Cell = variation.property match {
    case Property.Temperature => Cell(temperature + variation.value, humidity, pressure)
    case Property.Humidity => Cell(temperature, humidity + variation.value, pressure)
    case Property.Pressure => Cell(temperature, humidity, pressure + variation.value)
  }
}
