package scala.model.property

/**
 * Enumeration of all possible properties in the system.
 * It also include a bunch of utility functions.
 *
 * @author Paolo Baldini
 */
object Property extends Enumeration {
  type Property = Value
  val Temperature, Humidity, Pressure = Value

  case class PropertyValues(min: Int, max:Int, default: Int)

  /**
   * Return static/constant values associated to a property
   *
   * @param propertyValue value of the property (to be identified)
   * @return a tuple representing [MIN VALUE, MAX VALUE, DEFAULT VALUE]
   */
  def range(propertyValue: Value): PropertyValues = propertyValue match {
    case Temperature => PropertyValues(-50, 50, 10) // celsius
    case Humidity => PropertyValues(0, 100, 30)     // percentage
    case Pressure => PropertyValues(-5, 5, 1)       // atmosphere
  }

  /**
   * Convert the given value into a percentage on the range of the corresponding property
   *
   * @param propertyValue value of the property (to be identified)
   * @param value the value to convert into range
   * @return the converted value
   */
  def toPercentage(propertyValue: Value, value: Int): Int =
    100 * (value - range(propertyValue).min) / (range(propertyValue).max - range(propertyValue).min)
}