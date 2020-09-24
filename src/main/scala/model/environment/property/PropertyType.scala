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
  sealed trait PropertyValue[T <: Property] {
    def property: T

    /**
     * Allow to get the property value directly without access the 'property' field
     *
     * @return the property instantiation
     */
    def apply(): T = property
  }

  val Temperature: Val with PropertyValue[TemperatureProperty] = TemperatureProperty
  val Humidity: Val with PropertyValue[HumidityProperty] = HumidityProperty
  val Pressure: Val with PropertyValue[PressureProperty] = PressureProperty

  def properties(filterCondition: Property => Boolean = _ => true): Iterable[PropertyValue[Property]] =
    values.asInstanceOf[Iterable[PropertyValue[Property]]].filter(filterCondition(_))

  def random(filterCondition: Property => Boolean = _ => true): Option[PropertyValue[Property]] =
    properties().filter(filterCondition(_)).random

  implicit def getPropertyFrom[T <: Property](entry: PropertyValue[T]): T = entry()

  private implicit def enumValueOf[T <: Property](_property: T): Val with PropertyValue[T] =
    new Val with PropertyValue[T] { override def property: T = _property }
}