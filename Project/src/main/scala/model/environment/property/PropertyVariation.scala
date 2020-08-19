package scala.model.environment.property

import scala.model.environment.matrix.Point

trait PropertyVariation[T <: Property] {
  def value: T#ValueType
}
object PropertyVariation {
  case class Variation[T <: Property](value: T#ValueType) extends PropertyVariation[T]
  case class PointVariation[T <: Property](value: T#ValueType, x: Int, y: Int) extends PropertyVariation[T] with Point

  def vary[T <: Property](value: T#ValueType, variation: PropertyVariation[T])
  (implicit operation: (PropertyVariation[T], T#ValueType) => T#ValueType) : T#ValueType = operation(variation, value)
}