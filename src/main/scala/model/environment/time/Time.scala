package scala.model.environment.time

sealed trait Time {
  def days: Int
  def month: Int
  def year: Int
}

object Time extends Ordering[Time] {
  private var _time: Time = 0

  private class TimeImpl(val _days: Int) extends Time {
    override def days: Int = _days % 30

    override def month: Int = (_days / 30) % 12

    override def year: Int = _days / 365
  }

  def now(): Time = _time

  def delay(days: Int, from: Time = Time.now()): Time = from + days

  def increment(days: Int = 1): Unit = days match {
    case value if value > 0 => _time = _time + value
  }

  def reset(): Unit = _time = 0

  implicit def toTime(days: Int): Time = new TimeImpl(days)

  implicit def toDays(time: Time): Int = time.year * 365 + time.month * 30 + time.days

  override def compare(first: Time, second: Time): Int = first - second
}