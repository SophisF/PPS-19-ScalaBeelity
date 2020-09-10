package scala.model.environment.property.realization

import scala.model.environment.property.Property

/**
 * Simplify the use (and help DRY) of numeric properties
 *
 * @author Paolo Baldini
 */
trait IntegerProperty extends Property {
  override type ValueType = Int
}/*
  case class IntegerVariation(value: Int) extends Variation {
    override def vary[S <: State](state: S): S = toState(state.value + value)
  }
}

object IntegerProperty {
  implicit def toState[S <: IntegerProperty#State](value: Int): S

  implicit def toVariation(value: Int = default): Variation = IntegerVariation(value)

  implicit def toValue(state: State): Int = state.value
}*/