package scala.model

import scala.model.property.Property.{Property, Temperature, Humidity, Pressure}
import scala.model.property.PropertyVariation
import scala.model.property.PropertyVariation._

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
    case Temperature => temperature
    case Humidity => humidity
    case Pressure => pressure
  }

  def +(variation: Option[Variation]): Cell = variation match {
    case None => this
    case _ => this + variation.get
  }

  /**
   * Variate a specific property of the cell summing the value specified by the variation
   *
   * @param variation contains value and target of the variation
   * @return the varied cell
   */
  def +(variation: Variation): Cell = variation.property match {
    case Temperature => Cell(temperature + variation.value, humidity, pressure)
    case Humidity => Cell(temperature, humidity + variation.value, pressure)
    case Pressure => Cell(temperature, humidity, pressure + variation.value)
  }
}
