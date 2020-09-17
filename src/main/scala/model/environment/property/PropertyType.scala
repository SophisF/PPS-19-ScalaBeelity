package scala.model.environment.property

import scala.model.environment.property.realization._
import scala.model.environment.utility.SequenceHelper.RichSequence

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
  case class PropertyValue[T <: Property] private(property: T) extends Val() {

    /**
     * Allow to get the property value directly without access the 'property' field
     *
     * @return the property instantiation
     */
    def apply(): T = property
  }

  val Temperature: PropertyValue[TemperatureProperty] = PropertyValue(TemperatureProperty)
  val Humidity: PropertyValue[HumidityProperty] = PropertyValue(HumidityProperty)
  val Pressure: PropertyValue[PressureProperty] = PropertyValue(PressureProperty)

  def properties(filterCondition: Property => Boolean = _ => true): Seq[PropertyValue[Property]] =
    values.toSeq.asInstanceOf[Seq[PropertyValue[Property]]].filter(it => filterCondition(it()))

  def random(filterCondition: Property => Boolean = _ => true): Option[PropertyValue[Property]] =
    properties().filter(property => filterCondition(property())).random()
}