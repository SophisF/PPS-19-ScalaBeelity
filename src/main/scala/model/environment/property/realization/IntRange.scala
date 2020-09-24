package scala.model.environment.property.realization

import scala.model.environment.property.Range

trait IntRange extends Range {
  override type ValueType = Int

  def rangeWidth: Int = maxValue - minValue
  def rangeCenter: Int = minValue + rangeWidth / 2
  def zeroCenteredRange: (Int, Int) = (minValue - rangeCenter, maxValue - rangeCenter)
}