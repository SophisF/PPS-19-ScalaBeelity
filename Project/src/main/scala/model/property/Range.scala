package scala.model.property

trait Range {
  type ValueType

  def minValue: ValueType
  def maxValue: ValueType

  // TODO implicit re-ranging
  def limit(value: ValueType)(implicit ordering: Ordering[ValueType]): ValueType = value match {
    case value if ordering.compare(value, maxValue) >= 0 => maxValue
    case value if ordering.compare(value, minValue) <= 0 => minValue
    case _ => value
  }
}
