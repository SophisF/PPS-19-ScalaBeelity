package scala.model.environment.property.realization.integer

import org.scalatest.funsuite.AnyFunSuite

/**
 * Test Range trait of type Int.
 * Further tests were made in superclass
 */
class RangeTest extends AnyFunSuite {

  private val range: Range = new Range() {
    override def minValue: Int = -2

    override def maxValue: Int = 8
  }

  test("Range should give a correct center of the range") {
    assert(range.rangeCenter equals 3)
  }

  test("Range should give the max value of the range") {
    assert(range.maxValue equals 8)
  }

  test("Range should give the min value of the range") {
    assert(range.minValue equals -2)
  }

  test("Range should give correct width of the range") {
    assert(range.rangeWidth equals 10)
  }

  test("Range should give a correct range centered of 0") {
    assert(range.zeroCenteredRange equals(-5, 5))
  }

  // FURTHER TESTS WERE MADE IN SUPERCLASS
}
