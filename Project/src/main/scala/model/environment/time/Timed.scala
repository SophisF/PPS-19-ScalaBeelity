package scala.model.environment.time

/**
 * Represent a duration
 *
 * @author Paolo Baldini
 */
trait Timed {
  def fireTime: Int
  def duration: Int
}
object Timed {

  def isEnded(obj: Timed, time: Int = Time.time): Boolean = obj.fireTime + obj.duration < time

  /**
   * Check if start time has yet been reached
   *
   * @param obj to check start
   * @return true if start time is previous to actual time; false otherwise
   */
  def isStarted(obj: Timed, time: Int = Time.time): Boolean = obj.fireTime <= time

  def inProgress(obj: Timed, time: Int = Time.time): Boolean = isStarted(obj, time) && !isEnded(obj, time)
}