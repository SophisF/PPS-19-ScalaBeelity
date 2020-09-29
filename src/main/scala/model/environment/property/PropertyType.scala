package scala.model.environment.property

import scala.model.environment.property.realization._
import scala.reflect.ClassTag
import scala.utility.IterableHelper.RichIterable

/** Enumeration of the possible property types */
object PropertyType extends Enumeration {

  /**
   * A non-externally-instantiable class that contains the property type and instantiation (for most one, a singleton)
   *
   * @tparam T type of property
   */
  sealed trait PropertyValue[T <: Property] {

    /**
     * A property instantiation containing his usage information
     *
     * @return the property object who contains usage information
     */
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

  def propertiesType: Iterable[PropertyValue[Property]] = values.collect { case it: PropertyValue[Property] => it }

  def properties[T: ClassTag]: Iterable[T] = propertiesType.map(propertyOf).collect { case it: T => it.asInstanceOf[T] }

  def random[T: ClassTag]: Option[T] = properties[T].random

  /**
   * Returns an iterable of properties possibly filtered with a condition
   *
   * @param filterCondition filter the properties returning only the ones which match the condition
   * @return the iterable of properties
   */
  //def properties(filterCondition: Property => Boolean = _ => true): Iterable[PropertyValue[Property]] =
  //  values.asInstanceOf[Iterable[PropertyValue[Property]]].filter(filterCondition(_))

  /**
   * Get a random property from the ones that match the given condition
   *
   * @param filterCondition a condition that a property should match to be eligible to be returned
   * @return a random property from the eligible ones
   */
  //def random(filterCondition: Property => Boolean = _ => true): Option[PropertyValue[Property]] =
  //  properties().filter(filterCondition(_)).random

  /**
   * Utility function to automatically convert from a PropertyValue to a Property
   *
   * @param entry the property-value of which take the property
   * @tparam T the type of the property to be returned
   * @return the property held by the property-value
   */
  implicit def propertyOf[T <: Property](entry: PropertyValue[T]): T = entry()

  private implicit def enumValueOf[T <: Property](_property: T): Val with PropertyValue[T] =
    new Val with PropertyValue[T] { override def property: T = _property }
}