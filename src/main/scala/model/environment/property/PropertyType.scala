package scala.model.environment.property

import scala.model.environment.property.realization.{HumidityProperty, PressureProperty, TemperatureProperty}

/**
 * Enumeration of all the possible property types
 *
 * @author Paolo Baldini
 */
object PropertyType extends Enumeration {

  /**
   * A non externally instantiable class that contains the property type and instantiation (for most one, a singleton)
   *
   * @param property instantiated object
   * @tparam T type of property
   */
  case class PropertyTypeValue[T <: Property] private (property: T) extends Val() {

    /**
     * Allow to get the property value directly without access the 'property' field
     *
     * @return the property instantiation
     */
    def apply(): T = property
  }

  val Temperature: PropertyTypeValue[TemperatureProperty] = PropertyTypeValue(TemperatureProperty)
  val Humidity: PropertyTypeValue[HumidityProperty] = PropertyTypeValue(HumidityProperty)
  val Pressure: PropertyTypeValue[PressureProperty] = PropertyTypeValue(PressureProperty)
}