package scala.model.time

import scala.model.time.Timed.inProgress

/**
 * TODO valori variazione stagionali verranno settati dal controller (magari introducendo dei val max-min)
 */

/**
 * Represent a data whose value is related to time
 *
 * @tparam R type of data returned
 */
trait TimeData[R]
object TimeData {

  /**
   * Obtain the value of the data at actual instant through use of a operation
   *
   * @param data to which get the timed-data
   * @param operation to calculate the timed-data based on a specific strategy
   * @tparam R type of the returned data
   * @tparam T type of the timed-data
   * @return the data at actual time
   */
  def dataAtInstant[R, T <: TimeData[R]](data: T)(implicit operation: T => R): R = operation(data)
}

/**
 * Represent a data whose value is related to a specific life-time
 *
 * @tparam R type of data returned
 */
trait FiniteData[R] extends TimeData[R] with Timed {
  def evaluated: Int
}
object FiniteData {

  /**
   * Obtain the value of the data at actual instant through use of a operation.
   * If the data is dead (out of lifetime), None is returned
   *
   * @param data to which get the timed-data
   * @param operation to calculate the timed-data based on a specific strategy
   * @tparam R type of the returned data
   * @tparam T type of the timed-data
   * @return the data at actual time or null if the data is dead (out of lifetime)
   */
  def dataAtInstant[R, T <: FiniteData[R]](data: T)(implicit operation: T => R): Option[R] =
    Option.when(inProgress(data, Time.time))(operation(data))
}