package scala.model

sealed trait Time {
  def days: Int
  def month: Int
  def year: Int
}

object Time extends Ordering[Time] {
  private var _time: Time = 0
  private var _incrementValue: Int = 1

  private class TimeImpl(val _days: Int) extends Time {
    override def days: Int = _days - year * 365 - month * 30

    override def month: Int = (_days - year * 365) / 30

    override def year: Int = _days / 365
  }

  def now(): Time = _time
  def time(): Int = toDays(_time) // TODO remove

  def delay(days: Int, from: Time = Time.now()): Time = from + days

  def increment(days: Int = _incrementValue): Unit = days match {
    case value if value > 0 => _time = _time + value
  }

  def reset(): Unit = _time = 0
  def initialize(): Unit = _time = 0  // TODO remove

  implicit def toTime(days: Int): Time = new TimeImpl(days)

  implicit def toDays(time: Time): Int = time.year * 365 + time.month * 30 + time.days

  override def compare(first: Time, second: Time): Int = first - second

  def elapsed(instant: Time, days: Int): Boolean = _time >= instant + days

  def incrementValue: Int = _incrementValue

  def setIncrementValue(value: Int = 1): Unit = value match {
    case value if value > 0 => _incrementValue = value
    case _ =>
  }
}