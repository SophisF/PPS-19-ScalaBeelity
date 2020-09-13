package scala.model.environment.property

import scala.model.environment.property.realization._
import scala.util.Random

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
  case class PropertyValue[T <: Property] private(property: T, helper: PropertyHelper[T]) extends Val() {

    /**
     * Allow to get the property value directly without access the 'property' field
     *
     * @return the property instantiation
     */
    def apply(): T = property
  }

  val Temperature: PropertyValue[TemperatureProperty] = PropertyValue(TemperatureProperty, TemperaturePropertyHelper)
  val Humidity: PropertyValue[HumidityProperty] = PropertyValue(HumidityProperty, HumidityPropertyHelper)
  val Pressure: PropertyValue[PressureProperty] = PropertyValue(PressureProperty, PressurePropertyHelper)

  def properties: Seq[PropertyValue[Property]] = super.values.toSeq.asInstanceOf[Seq[PropertyValue[Property]]]

  def random(): PropertyValue[Property] = properties.toIndexedSeq(Random.nextInt(values.size))
}