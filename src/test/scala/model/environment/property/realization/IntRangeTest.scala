package scala.model.environment.property.realization

import org.scalatest.funsuite.AnyFunSuite

class IntRangeTest extends AnyFunSuite {

  val range: IntRange = new IntRange() {
    override def minValue: Int = 0

    override def maxValue: Int = 2
  }

  test("IntRange should give a correct center of the range") {
    assert(range.rangeCenter equals 1)
  }

  test("IntRange should give the max value of the range") {
    assert(range.maxValue equals 2)
  }

  test("IntRange should give the min value of the range") {
    assert(range.minValue equals 0)
  }

  test("IntRange should give correct width of the range") {
    assert(range.rangeWidth equals 2)
  }

  test("IntRange should give a correct range centered of 0") {
    assert(range.zeroCenteredRange equals(-1, 1))
  }
}
