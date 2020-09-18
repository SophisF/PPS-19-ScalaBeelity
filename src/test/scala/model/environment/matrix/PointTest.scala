package model.environment.matrix

import org.scalatest.funsuite.AnyFunSuite

import scala.utility.Point

class PointTest extends AnyFunSuite {
  private val P = "Point"


  test(s"$P  must have the coordinates, passed in apply function.") {
    var result = Point apply(1, 2)

    assert(result.x.equals(1) && result.y.equals(2))
  }

  test(s"$P with reassignment, must have the last coordinates.") {
    var result = Point apply(1, 2)
    result = Point apply(4,5)

    assert(result.x.equals(4) && result.y.equals(5))
  }
}
