package scala.model.environment.property

object Property extends Enumeration {
  type Property = Value
  val Temperature, Humidity, Pressure = Value

  case class PropertyData(minValue: Int, maxValue: Int, default: Int)

  /**
   * Return static/constant values associated to a property
   *
   * @param propertyValue value of the property (to be identified)
   * @return a tuple representing [MIN VALUE, MAX VALUE, DEFAULT VALUE]
   */
  def range(propertyValue: Value): PropertyData = propertyValue match {
    case Temperature => PropertyData(-50, 50, 10) // celsius
    case Humidity => PropertyData(0, 100, 30)     // percentage
    case Pressure => PropertyData(-5, 5, 1)       // atmosphere
  }

  /**
   * Convert the given value into a percentage on the range of the corresponding property
   *
   * @param propertyValue value of the property (to be identified)
   * @param value the value to convert into range
   * @return the converted value
   */
  def toPercentage(propertyValue: Value, value: Int): Int =
    100 * (value - range(propertyValue).minValue) / (range(propertyValue).maxValue - range(propertyValue).minValue)
}