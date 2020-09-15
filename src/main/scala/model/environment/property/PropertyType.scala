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

  def properties: Seq[PropertyValue[Property]] = super.values.toSeq.asInstanceOf[Seq[PropertyValue[Property]]]

  def random(filterCondition: Property => Boolean = _ => true): Option[PropertyValue[Property]] = properties
    .filter(property => filterCondition(property())).toIndexedSeq match {
    case seq if seq.isEmpty => Option.empty
    case seq => Option(seq(Random.nextInt(seq.size)))
  }
}