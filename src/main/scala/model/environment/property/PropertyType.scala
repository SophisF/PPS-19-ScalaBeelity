package scala.model.environment.property

import scala.model.environment.property.realization._
import scala.utility.IterableHelper.RichIterable

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
  sealed trait PropertyValue[T <: Property] extends Val {
    def property: T

    /**
     * Allow to get the property value directly without access the 'property' field
     *
     * @return the property instantiation
     */
    def apply(): T = property
  }

  val Temperature: PropertyValue[TemperatureProperty] = TemperatureProperty
  val Humidity: PropertyValue[HumidityProperty] = HumidityProperty
  val Pressure: PropertyValue[PressureProperty] = PressureProperty

  def properties(filterCondition: Property => Boolean = _ => true): Iterable[PropertyValue[Property]] =
    values.asInstanceOf[Iterable[PropertyValue[Property]]].filter(filterCondition(_))

  def random(filterCondition: Property => Boolean = _ => true): Option[PropertyValue[Property]] =
    properties().filter(filterCondition(_)).random

  implicit def getPropertyFrom[T <: Property](entry: PropertyValue[T]): T = entry()

  private implicit def enumValueOf[T <: Property](_property: T): PropertyValue[T] = new PropertyValue[T] {
    override def property: T = _property
  }
}