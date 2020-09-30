package scala.model.environment.matrix

import org.scalatest.funsuite.AnyFunSuite

import scala.model.environment.matrix.Size.Border

/**
 * Test for Zone trait/object
 */
class ZoneTest extends AnyFunSuite {

  private case class ZoneImpl(x: Int, y: Int, width: Int, height: Int) extends Zone

  test("'border' method with Top should return the first row of the zone") {
    assert(Zone.border(ZoneImpl(10, 10, 5, 5))(Border.Top) == 8)
  }

  test("'border' method with Bottom should return the last row of the zone") {
    assert(Zone.border(ZoneImpl(10, 10, 5, 5))(Border.Bottom) == 12)
  }

  test("'border' method with Left should return the last column of the zone") {
    assert(Zone.border(ZoneImpl(10, 10, 5, 5))(Border.Left) == 8)
  }

  test("'border' method with Right should return the first column of the zone") {
    assert(Zone.border(ZoneImpl(10, 10, 5, 5))(Border.Right) == 12)
  }

  test("'border' method should calculate also negative row") {
    assert(Zone.border(ZoneImpl(5, 5, 20, 20))(Border.Top) == -5)
  }

  test("'border' method should calculate also negative column") {
    assert(Zone.border(ZoneImpl(5, 5, 20, 20))(Border.Left) == -5)
  }

  test("'in' method should state if an x/y point is inside the zone") {
    assert(Zone.in(5, 5, ZoneImpl(5, 5, 10, 10)))
  }

  test("'in' method should state if an x/y point is outside the zone") {
    assert(!Zone.in(0, 0, ZoneImpl(25, 25, 10, 10)))
  }
}
