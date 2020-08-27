package scala.model.environment.time

import scala.model.environment.time.Time.delay

/**
 * Represent a duration
 *
 * @author Paolo Baldini
 */
trait Timed {
  def fireTime: Time
  def daysDuration: Int
}

object Timed {

  def isEnded(o: Timed, time: Time = Time.now()): Boolean = Time.compare(delay(o.fireTime, o.daysDuration), time) < 0

  /**
   * Check if start time has yet been reached
   *
   * @param obj to check start
   * @return true if start time is previous to actual time; false otherwise
   */
  def isStarted(obj: Timed, time: Time = Time.now()): Boolean = Time.compare(obj.fireTime, time) <= 0

  def inProgress(obj: Timed, time: Time = Time.now()): Boolean = isStarted(obj, time) && !isEnded(obj, time)
}