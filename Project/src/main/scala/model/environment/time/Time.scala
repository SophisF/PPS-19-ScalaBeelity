package scala.model.environment.time

/**
 * Ecosystem time
 */
object Time {
  private var _time: Int = 0

  /**
   * Get the actual time of the ecosystem
   *
   * @return the actual time
   */
  def time: Int = _time

  /**
   * Increment the ecosystem time. The minimum granularity of the system is 1 day
   *
   * @param value the time increment
   */
  def increment(value: Int = 1): Unit = value match {
    case value if value > 0 => _time += value
    case _ =>
  }

  /**
   * Initialize the ecosystem time.
   */
  def initialize(): Unit = _time = 0
}