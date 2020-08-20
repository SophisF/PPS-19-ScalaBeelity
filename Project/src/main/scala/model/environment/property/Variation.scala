package scala.model.environment.property

import scala.model.environment.matrix.Point

/**
 * Represent the variation of a property
 *
 * @tparam T type of the property
 *
 * @author Paolo Baldini
 */
trait Variation[T <: Property] {
  def value: T#ValueType
}

object Variation {
  case class GenericVariation[T <: Property](value: T#ValueType) extends Variation[T]
  case class PointVariation[T <: Property](value: T#ValueType, x: Int, y: Int) extends Variation[T] with Point

  /**
   * Vary a value by the specified variation
   *
   * @param value to vary
   * @param variation the the property value
   * @param operation to vary the value tby the variation
   * @tparam T type of property
   * @return the varied value
   */
  def vary[T <: Property](value: T#ValueType, variation: Variation[T])(implicit operation: (Variation[T], T#ValueType)
    => T#ValueType): T#ValueType = operation(variation, value)
}