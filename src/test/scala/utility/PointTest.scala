package scala.utility

import org.scalatest.funsuite.AnyFunSuite

import scala.utility.Point.toPoint

class PointTest extends AnyFunSuite {

  test("Same point should been equal.") {
    val point1 = Point(1, 2)
    assert(Point.equals(point1, point1))
  }

  test("Two different points shouldn't been equals.") {
    val point1 = Point(1, 2)
    val point2 = Point(3, 4)
    assert(!Point.equals(point1, point2))
  }

  test("Two different points with same coordinates should been equals.") {
    val point1 = Point(1, 2)
    val point2 = Point(1, 2)
    assert(Point.equals(point1, point2))
  }

  test("Create point to tuple or coordinate have the same result") {
    val point1 = Point(1, 2)
    val point2 = toPoint((1, 2))
    assert(Point.equals(point1, point2))
  }

}
