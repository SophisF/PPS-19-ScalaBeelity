package scala.model.environment.matrix

import org.scalatest.funsuite.AnyFunSuite

import scala.model.environment.matrix.Size.Border

/**
 * Test for Size trait/object
 */
class SizeTest extends AnyFunSuite {

  test("Size apply should set correct 'width' field") {
    assert(Size(_width = 10, _height = 0).width == 10)
  }

  test("Size apply should set correct 'height' field") {
    assert(Size(_width = 0, _height = 10).height == 10)
  }

  test("A tuple should be correctly convertible to size") {
    assert(Size.toSize((10, 10)).isInstanceOf[Size])
  }

  test("'~' method with Top should return the 'movement' from the center to reach the top border") {
    assert(Size(9, 9) ~ Border.Top == -4)
  }

  test("'~' method with Top should work also with even border size (1 more step compared to odd)") {
    assert(Size(10, 10) ~ Border.Top == -5)
  }

  test("'~' method with Bottom should return the 'movement' from the center to reach the bottom border") {
    assert(Size(9, 9) ~ Border.Bottom == 4)
  }

  test("'~' method with Bottom should work also with even border size (1 more step compared to odd)") {
    assert(Size(10, 10) ~ Border.Bottom == 5)
  }

  test("'~' method with Left should return the 'movement' from the center to reach the left border") {
    assert(Size(9, 9) ~ Border.Left == -4)
  }

  test("'~' method with Left should work also with even border size (1 more step compared to odd)") {
    assert(Size(10, 10) ~ Border.Left == -5)
  }

  test("'~' method with Right should return the 'movement' from the center to reach the right border") {
    assert(Size(9, 9) ~ Border.Right == 4)
  }

  test("'~' method with Right should work also with even border size (1 more step compared to odd)") {
    assert(Size(10, 10) ~ Border.Right == 5)
  }

  test("'~' method with Top should return 0 with a 0-height size") {
    assert(Size(0, 0) ~ Border.Top == 0)
  }

  test("'~' method with Bottom should return 0 with a 0-height size") {
    assert(Size(0, 0) ~ Border.Bottom == 0)
  }

  test("'~' method with Left should return 0 with a 0-width size") {
    assert(Size(0, 0) ~ Border.Left == 0)
  }

  test("'~' method with Right should return 0 with a 0-width size") {
    assert(Size(0, 0) ~ Border.Right == 0)
  }
}
