package scala.model.environment.property
/*
import org.scalatest.funsuite.AnyFunSuite

/**
 * Test range trait/object
 *
 * @author Paolo Baldini
 */
class RangeTest extends AnyFunSuite {

  object Range_ extends Range {
    type ValueType = Int
    def minValue: Int = 0
    def maxValue: Int = 10
  }

  test("Limit function should return the passed value if it's in-range") {
    assert(Range.limit(Range_)(Range_.maxValue -1) == Range_.maxValue -1)
  }

  test("Limit function should return the max-value if the passed one is over the range") {
    assert(Range.limit(Range_)(Range_.maxValue +1) == Range_.maxValue)
  }

  test("Limit function should return the min-value if the passed one is under the range") {
    assert(Range.limit(Range_)(Range_.minValue -1) == Range_.minValue)
  }
}
*/