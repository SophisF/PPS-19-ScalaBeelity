package scala.model.environment.property

/**
 * Set a maximum an minimum value to something.
 */
private[property] trait Range {
  type ValueType

  /**
   * Minimum value
   *
   * @return value
   */
  def minValue: ValueType

  /**
   * Maximum value.
   *
   * @return value
   */
  def maxValue: ValueType

  /**
   * Set the passed value in range
   *
   * @param value    to re-range
   * @param ordering of the value type
   * @return a re-ranged value
   */
  implicit def limit(value: ValueType)(implicit ordering: Ordering[ValueType]): ValueType = value match {
    case value if ordering.compare(value, maxValue) >= 0 => maxValue
    case value if ordering.compare(value, minValue) <= 0 => minValue
    case _ => value
  }
}