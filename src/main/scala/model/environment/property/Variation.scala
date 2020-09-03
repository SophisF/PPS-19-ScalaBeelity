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

  implicit def toValue[T <: Property](variation: Variation[T]): Property#ValueType = variation.value
}