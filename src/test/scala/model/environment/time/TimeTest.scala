package scala.model.environment.time

import org.scalatest.funsuite.AnyFunSuite

import scala.model.Time

/**
 * Test class for the time entity
 *
 * @author Paolo Baldini
 */
class TimeTest extends AnyFunSuite {

  test("Time should start at 0") {
    Time.reset()
    assert(Time.dayTime == 0)
  }

  test("Time should be different after increment") {
    val time = Time.dayTime()

    Time.increment(0.toInt)
    assert(Time.dayTime == time)

    Time.increment(1.toInt)
    assert(Time.dayTime == time + 1)

    Time.increment(5.toInt)
    assert(Time.dayTime == time + 1 + 5)
  }

  test("Time should be equal after a negative increment (i.e., should not change)") {
    Time.increment(5.toInt)
    val time = Time.dayTime

    Time.increment(-100.toInt)
    assert(Time.dayTime == time)
  }

  test("Time initialize should reset the time to 0") {
    Time.increment(5)

    Time.reset()
    assert(Time.dayTime == 0)
  }
}
