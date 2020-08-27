package scala.model.environment.time
/*
import scala.model.environment.time.Time._

import org.scalatest.funsuite.AnyFunSuite

/**
 * Test class for the time entity
 *
 * @author Paolo Baldini
 */
class TimeTest extends AnyFunSuite {

  test("Time should start at 0") {
    assert(toDays(Time.now()) == 0)
  }

  test("Time should be different after increment") {
    val time = Time.time

    Time.increment(0)
    assert(Time.time == time)

    Time.increment(1)
    assert(Time.time == time + 1)

    Time.increment(5)
    assert(Time.time == time + 1 + 5)
  }

  test("Time should be equal after a negative increment (i.e., should not change)") {
    Time.increment(5)
    val time = Time.time

    Time.increment(-100)
    assert(Time.time == time)
  }

  test("Time initialize should reset the time to 0") {
    Time.increment(5)

    Time.initialize()
    assert(Time.time == 0)
  }
}
*/