package scala.model.environment.time

import scala.model.Time
import scala.model.Time.{compare, delay}

/**
 * Represent a duration
 */
private[environment] trait Timed {
  def fireTime: Time
  def daysDuration: Int
}

private[environment] object Timed {

  def isEnded(timed: Timed): Boolean = compare(delay(timed daysDuration, timed fireTime), Time.now()) < 0

  /**
   * Check if start time has yet been reached
   *
   * @param timed to check start
   * @return true if start time is previous to actual time; false otherwise
   */
  def isStarted(timed: Timed): Boolean = compare(timed fireTime, Time.now()) <= 0

  def inProgress(timed: Timed): Boolean = isStarted(timed) && !isEnded(timed)
}