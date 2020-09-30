package scala.utility

import org.scalatest.funsuite.AnyFunSuite

import scala.utility.Point.toPoint

class PointTest extends AnyFunSuite {

  private val P = "Point"


  test(s"$P  must have the coordinates, passed in apply function.") {
    var result = Point.apply(1, 2)

    assert(result.x.equals(1) && result.y.equals(2))
  }

  test(s"$P with reassignment, must have the last coordinates.") {
    var result = Point.apply(1, 2)
    result = Point.apply(4,5)

    assert(result.x.equals(4) && result.y.equals(5))
  }

  test(s"Same $P should been equal.") {
    val point1 = Point(1, 2)
    assert(Point.equals(point1, point1))
  }

  test(s"Two different $P shouldn't been equals.") {
    val point1 = Point(1, 2)
    val point2 = Point(3, 4)
    assert(!Point.equals(point1, point2))
  }

  test(s"Two different $P with same coordinates should been equals.") {
    val point1 = Point(1, 2)
    val point2 = Point(1, 2)
    assert(Point.equals(point1, point2))
  }

  test(s"Create $P to tuple or coordinate have the same result") {
    val point1 = Point(1, 2)
    val point2 = toPoint((1, 2))
    assert(Point.equals(point1, point2))
  }

}
