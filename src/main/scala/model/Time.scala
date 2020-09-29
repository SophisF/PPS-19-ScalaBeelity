package scala.model

import scala.Option.when
import scala.utility.SugarBowl.RichOptional

/**
 * Represents the time in the system.
 *
 * @author Paolo Baldini
 */
private[model] sealed trait Time {

  /**
   * Return the days of the month.
   * It does NOT return the days since the simulation start
   *
   * @return the days of the month
   */
  def days: Int

  /**
   * Return the month of the year.
   * It does NOT return the months since the simulation start
   *
   * @return the month of the year
   */
  def month: Int

  /**
   * Return the year of the simulation.
   * It returns the years elapsed since the simulation start
   *
   * @return the years in the simulation
   */
  def year: Int
}

private[model] object Time extends Ordering[Time] {
  private var _time: Time = 0
  private var _incrementValue: Int = 1

  private class TimeImpl(val _days: Int) extends Time {
    override def days: Int = _days - year * 365 - month * 30

    override def month: Int = (_days - year * 365) / 30

    override def year: Int = _days / 365
  }

  /**
   * Actual time of the simulation
   *
   * @return the actual time of the simulation
   */
  def now(): Time = _time

  /**
   * Returns the raw days elapsed since the simulation start.
   * It is an utility method not necessarily needed to work with time.
   * Consider also to use `daysFrom(now())`
   *
   * @return the days elapsed since simulation start
   */
  def dayTime(): Int = daysFrom(_time)

  /**
   * Returns the instant in which we will be after N days starting from the specified time instant
   *
   * @param days to delay
   * @param from time instant to which start
   * @return the 'delayed' time instant
   */
  def delay(days: Int, from: Time = now()): Time = from + days

  /**
   * Increment the time of the simulation
   *
   * @param days to which increment. Needs to be greater than zero
   */
  def increment(days: Int = _incrementValue): Unit = _time = delay(Math.max(0, days))

  /** Reset time to zero */
  def reset(): Unit = _time = 0

  /**
   * Returns a time object from an integer specifying the days
   *
   * @param days to convert to time
   * @return the time corresponding to the given raw days
   */
  implicit def timeFrom(days: Int): Time = new TimeImpl(days)

  /**
   * Given a time, returns the raw days since the simulation start
   *
   * @param time to convert to raw days
   * @return the raw days since the simulation start
   */
  implicit def daysFrom(time: Time): Int = time.year * 365 + time.month * 30 + time.days

  /**
   * Returns an integer whose sign communicates how x compares to y.
   *
   * The result sign has the following meaning:
   * - negative if x < y
   * - positive if x > y
   * - zero otherwise (if x == y)
   */
  override def compare(first: Time, second: Time): Int = first - second

  /**
   * Tell if the specified days are elapsed since the specified time
   *
   * @param instant to which start
   * @param days the days to 'add'
   * @return true if the specified days are elapsed since the specified time, false otherwise
   */
  def elapsed(instant: Time, days: Int): Boolean = _time >= instant + days

  /**
   * Get the default increment time value for the simulation
   *
   * @return the default increment time value
   */
  def incrementValue: Int = _incrementValue

  /**
   * Set the default increment time value for the simulation
   *
   * @param value to which increment. Needs to be greater than 0
   */
  def incrementValue_=(value: Int = 1): Unit = _incrementValue = when(value > 0)(value) ! _incrementValue
}