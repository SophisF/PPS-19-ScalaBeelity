package scala.model.environment.time

import org.scalatest.funsuite.AnyFunSuite

import scala.model.Time
import scala.model.environment.time.Timed.{inProgress, isEnded, isStarted}

/**
 * Test for timed entities
 *
 * @author Paolo Baldini
 */
class TimedTest extends AnyFunSuite {

  private def timed(_fireTime: Int, _duration: Int): Timed = new Timed {

    override def fireTime: Time = _fireTime

    override def daysDuration: Int = _duration
  }

  test("Ended function should return false when time is before the timed-data life (fireTime + duration)") {
    Time.reset()
    val _timed = timed(0, 100)
    Time increment 100

    assert(!isEnded(_timed))
  }

  test("Ended function should return true when time is after the timed-data life (fireTime + duration)") {
    Time.reset()
    val _timed = timed(0, 99)
    Time increment 100

    assert(isEnded(_timed))
  }

  test("Ended function should work also for fireTime different from 0") {
    Time increment 50
    val _timed = timed(Time.dayTime(), 100)
    Time increment 100

    assert(!isEnded(_timed))

    Time increment 1
    assert(isEnded(_timed))
  }

  test("Ended function should return true when duration is 0 (or eventually less)") {
    Time.reset()
    Time increment 2
    assert(isEnded(timed(0, 0)))
  }

  test("IsStarted function should return false when time is before the timed-data fireTime") {
    assert(!isStarted(timed(Time.dayTime + 1, 100)))
  }

  test("IsStarted function should return true when time is after the timed-data fireTime") {
    assert(isStarted(timed(Time.dayTime(), 100)))
  }

  test("IsStarted function should work also for fireTime 0") {
    Time.reset()
    val _timed = timed(0, 100)

    assert(isStarted(_timed))
  }

  test("IsStarted function should return true also when duration is 0") {
    assert(isStarted(timed(0, 0)))
  }

  test("InProgress function should return false when is not started") {
    assert(!inProgress(timed(Time.dayTime + 1, 100)))
  }

  test("InProgress function should return false when is ended") {
    assert(!inProgress(timed(Time.dayTime - 1, 0)))
  }

  test("InProgress function should return true when is started and not ended") {
    assert(inProgress(timed(Time.dayTime(), 100)))
  }

  test("InProgress function should work also for fireTime 0") {
    Time.reset()
    val _timed = timed(0, 100)

    assert(inProgress(_timed))
  }

  test("InProgress function should return false when duration is 0") {
    assert(inProgress(timed(0, 0)))
  }
}
