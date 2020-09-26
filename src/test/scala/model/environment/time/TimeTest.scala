package scala.model.environment.time

import org.scalatest.funsuite.AnyFunSuite

import scala.model.Time
import scala.model.Time._
import scala.utility.SugarBowl.RichMappable

/**
 * Test class for the time entity
 *
 * @author Paolo Baldini
 */
class TimeTest extends AnyFunSuite {

  test("Time should start at 0") {
    assert(reset() ~> (_ => dayTime()) == 0)
  }

  test("A time object should contains the correct number of days of a month") {
    assert(delay(35, 0).days == 5)
  }

  test("A time object should contains the correct number of month of a year") {
    assert(delay(160, 0).month == 5)
  }

  test("A time object should contains the correct number of years since simulation start") {
    assert(delay(900, 0).year == 2)
  }

  test("'now' return should change after increment") {
    assert(compare(now(), increment(5) ~> (_ => now())) < 0)
  }

  test("'dayTime' return should change after increment") {
    assert(compare(dayTime(), increment(5) ~> (_ => dayTime())) < 0)
  }

  test("'delay' with positive values should return a greater temporal instant") {
    assert(daysFrom(delay(30)) == dayTime() + 30)
  }

  test("'delay' with negative values should return a lower temporal instant") {
    assert(daysFrom(delay(-30)) == dayTime() - 30)
  }

  test("Time should be equal after a 0 days increment") {
    val time = Time.dayTime()
    Time increment 0
    assert(Time.dayTime == time)
  }

  test("Time should be different after non-zero increment") {
    val time = Time.dayTime()
    Time increment 5
    assert(Time.dayTime == time + 5)
  }

  test("Time should be equal after a negative increment (a decrement)") {
    val time = Time.dayTime()
    Time increment -5
    assert(Time.dayTime == time)
  }

  test("Time initialize should reset the time to 0") {
    Time increment 5
    Time.reset()
    assert(Time.dayTime == 0)
  }

  test("'timeFrom' should return a coherent time instant (days check)") {
    assert(timeFrom(65).days == 5)
  }

  test("'timeFrom' should return a coherent time instant (month check)") {
    assert(timeFrom(155).month == 5)
  }

  test("'timeFrom' should return a coherent time instant (year check)") {
    assert(timeFrom(900).year == 2)
  }

  test("'daysFrom' should return a coherent days value") {
    assert(daysFrom(delay(5)) == dayTime() + 5)
  }

  test("Given two time where the second is greater than the first, compare should return a negative value") {
    assert(compare(now(), delay(5)) < 0)
  }

  test("Given two time where the second is lower than the first, compare should return a positive value") {
    assert(compare(now(), delay(-5)) > 0)
  }

  test("Given two equals time, compare should return 0") {
    assert(compare(now(), now()) == 0)
  }

  test("'elapsed' should return true when the actual time is greater than the delayed one") {
    assert(elapsed(delay(-10), 5))
  }

  test("'elapsed' should return false when the actual time is lower than the delayed one") {
    assert(!elapsed(delay(-5), 10))
  }

  test("'incrementValue' should initially be coherent to the set one") {
    incrementValue = 5
    assert(incrementValue == 5)
  }

  test("'incrementValue' cannot be set to 0 or lower") {
    incrementValue = -5
    assert(incrementValue > 0)
  }
}
