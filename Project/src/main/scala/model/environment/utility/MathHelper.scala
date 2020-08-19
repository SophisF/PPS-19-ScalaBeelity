package scala.model.environment.utility

import Numeric.Implicits._

/**
 * Move complexity adding mathematical functions
 *
 * @author Paolo Baldini
 */
object MathHelper {

  def sameSign[T: Numeric](first: T, second: T): Boolean = first.sign.toInt < second.sign.toInt
}
