package scala.model.environment.property

import org.scalatest.funsuite.AnyFunSuite

/**
 * Test range trait/object
 *
 * @author Paolo Baldini
 */
class RangeTest extends AnyFunSuite {

  object IntRange extends Range {
    type ValueType = Int
    def minValue: Int = 0
    def maxValue: Int = 10
  }

  test("Limit function should return the passed value if it's in-range") {
    assert(IntRange.limit(IntRange.maxValue -1) == IntRange.maxValue -1)
  }

  test("Limit function should return the max-value if the passed one is over the range") {
    assert(IntRange.limit(IntRange.maxValue +1) == IntRange.maxValue)
  }

  test("Limit function should return the min-value if the passed one is under the range") {
    assert(IntRange.limit(IntRange.minValue -1) == IntRange.minValue)
  }

  object HolidaySeason extends Enumeration with Range {
    override type ValueType = Value

    val Winter, Spring, Summer, Autumn = Value

    override def minValue: Value = Spring
    override def maxValue: Value = Summer
  }

  /********************************************************************************************************************
   * VALUE-TYPE WITH ORDERING
   ********************************************************************************************************************/

  test("Limit function should return the passed value if it's in-range also for an enumeration (sortable)") {
    assert(HolidaySeason.limit(HolidaySeason.Spring) == HolidaySeason.Spring)
  }

  test("If over the range, it should return the max value also for an enumeration (sortable)") {
    assert(HolidaySeason.limit(HolidaySeason.Autumn) == HolidaySeason.Summer)
  }

  test("If under the range, it should return the min value also for an enumeration (sortable)") {
    assert(HolidaySeason.limit(HolidaySeason.Winter) == HolidaySeason.Spring)
  }
}
