package scala.model.property

import org.scalatest.funsuite.AnyFunSuite

import scala.model.property.FilterBuilder._

class FilterBuilderTest extends AnyFunSuite {

  test("Two Dimensional Descent should return a fixed sequence") {
    val result = twoDimensionalPositiveDescent(50, 1).map(_.toInt).toArray
    assert(result sameElements Array(50, 30, 6))
  }

  test("Three Dimensional Descent should return a fixed sequence") {
    val result = threeDimensionalPositiveDescent(50, 1).map(_.toInt).data
    assert(result sameElements Array(0, 4, 6, 4, 0, 4, 18, 30, 18, 4, 6, 30, 50, 30, 6, 4, 18, 30, 18, 4, 0, 4, 6, 4, 0))
  }
}
