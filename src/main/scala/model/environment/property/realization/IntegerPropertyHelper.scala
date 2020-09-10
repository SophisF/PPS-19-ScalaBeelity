package scala.model.environment.property.realization

import scala.model.environment.property.PropertyHelper

/**
 * Provide some utilities methods related to properties whose value type is an integer
 *
 * @tparam T property to help
 *
 * @author Paolo Baldini
 */
trait IntegerPropertyHelper[T <: IntegerProperty] extends PropertyHelper[T] {

  /**
   * Convert generic numeric value (e.g. Double, Float) to ValueType (Int)
   *
   * @param value to convert to ValueType
   * @tparam N type of value
   * @return converted value
   */
  implicit def toValueType[N: Numeric](value: N): Int = implicitly[Numeric[N]].toInt(value)

  /**
   * Convert generic numeric value (e.g. Double, Float) to PropertyState
   *
   * @param value of which get the state
   * @tparam N type of value
   * @return converted value
   */
  implicit def toState[N: Numeric](value: N)(implicit _toState: Int => T#State): T#State =
    _toState(implicitly[Numeric[N]].toInt(value))
}