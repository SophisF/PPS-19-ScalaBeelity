package scala.model.environment.property.realization

import scala.model.environment.property.Property

/**
 * Simplify the use (and help DRY) of numeric properties
 *
 * @author Paolo Baldini
 */
trait IntegerProperty extends Property {
  override type ValueType = Int

  trait IntegerState extends State {
    implicit def apply(value: Int): IntegerState

    override def varyBy(variation: Int): State = value + variation
  }

  implicit def toValue(state: IntegerProperty#State): Int = state.value
}