package scala.utility

import scala.util.Random

object MathHelper {

  def randomBoolean(probability: Int = 50): Boolean = Random.nextInt(100) +1 <= probability

  /**
   * Convert generic numeric value (e.g. Double, Float) to ValueType (Int)
   *
   * @param value to convert to ValueType
   * @tparam N type of value
   * @return converted value
   */
  implicit def intValueOf[N: Numeric](value: N): Int = implicitly[Numeric[N]].toInt(value)
}
