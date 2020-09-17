package scala.model.environment.utility

object MathHelper {

  /**
   * Convert generic numeric value (e.g. Double, Float) to ValueType (Int)
   *
   * @param value to convert to ValueType
   * @tparam N type of value
   * @return converted value
   */
  implicit def intValue[N: Numeric](value: N): Int = implicitly[Numeric[N]].toInt(value)
}
