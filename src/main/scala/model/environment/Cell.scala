package scala.model.environment

import scala.model.environment.property.PropertyType.{Humidity, Pressure, Temperature}
import scala.model.environment.property.Variation.vary
import scala.model.environment.property.realization.{HumidityProperty, HumidityPropertyHelper, PressureProperty, PressurePropertyHelper, TemperatureProperty, TemperaturePropertyHelper}
import scala.model.environment.property.realization.TemperaturePropertyHelper.TemperatureHelper
import scala.model.environment.property.realization.HumidityPropertyHelper.HumidityHelper
import scala.model.environment.property.realization.PressurePropertyHelper.PressureHelper
import scala.model.environment.property.{Property, PropertyHelper, PropertyType, Variation}

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
  temperature: TemperatureProperty#State,
  humidity: HumidityProperty#State,
  pressure: PressureProperty#State
) {
  /*private val map = Map[PropertyType.Value, Property#State](
    Temperature -> temperature,
    Humidity -> humidity,
    Pressure -> pressure
  )*/

  /**
   * Used to iterate over the properties
   *
   * @param property of which we want to know the value
   * @return the value of the property
   */
  def apply[T <: Property](property: PropertyType.PropertyTypeValue[T]): T#State = property match {
    case Temperature => temperature.asInstanceOf[T#State]
    case Humidity => humidity.asInstanceOf[T#State]
    case Pressure => pressure.asInstanceOf[T#State]
  }/*map(property) match {
    case state: T#State => state.asInstanceOf[T#State]
  }*/

  def apply[T <: Property](property: PropertyType.Value): T#State = property match {
    case Temperature => temperature.asInstanceOf[T#State]
    case Humidity => humidity.asInstanceOf[T#State]
    case Pressure => pressure.asInstanceOf[T#State]
  }//map(property).asInstanceOf[T#State]

  def +[T <: Property](variation: Variation[T]): Cell = {
    variation match {
      case v:Variation[TemperatureProperty] => Cell(vary(temperature, v), humidity, pressure)
      case v:Variation[HumidityProperty] => Cell(temperature, vary(humidity, v), pressure)
      case v:Variation[PressureProperty] => Cell(temperature, humidity, vary(pressure, v))
      case _ => this
    }
  }

  /**
   * Make an optional sum. If the optional is empty, it returns the same cell;
   * otherwise exec the normal sum
   *
   * @param variation optionally contains value and target of the variation
   * @return the optionally varied cell
   */
  def +?[T <: Property](variation: Option[Variation[T]]): Cell = variation map (this + _) getOrElse this
}

object Cell {

  def apply(
    temperature: TemperatureProperty#ValueType = TemperatureProperty.default,
    humidity: HumidityProperty#ValueType = HumidityProperty.default,
    pressure: PressureProperty#ValueType = PressureProperty.default
  ): Cell = Cell(TemperaturePropertyHelper.toState(temperature), HumidityPropertyHelper.toState(humidity),
    PressurePropertyHelper.toState(pressure))

  /**
   * Apply field-wise operation between two cell. Resulting value cannot exceed property limits/range
   *
   * @param first a cell
   * @param second a cell
   * @param property the operation between element of 'first' and 'second'
   * @return a cell on which every field is the (limited) result of the operation applied to between the value of the
   *        'first' and 'second' cells
   */
  def operation(first: Cell, second: Cell): Cell = Cell(
    implicitly[PropertyHelper[TemperatureProperty]].sum(first(Temperature), second(Temperature)),
    implicitly[PropertyHelper[HumidityProperty]].sum(first(Humidity), second(Humidity)),
    implicitly[PropertyHelper[PressureProperty]].sum(first(Pressure), second(Pressure))
  )
}
