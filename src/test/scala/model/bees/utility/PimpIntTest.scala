package scala.model.bees.utility

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.utility.PimpInt._

class PimpIntTest extends AnyFunSuite{

  test("Integers must have the 'in' method to check whether the number belongs to a range") {
    val x: Int = 1
    assert(x in (0, 2))
  }

  test("Integers must have the 'in' method to check whether the number belongs to a range, including range boundaries") {
    val x: Int = 0
    val y: Int = 2
    assert((x in (0, 2)) && (y in (0,2)))
  }

  test("Integers must have < to check if a number is smaller than the bottom range boundaries") {
    val x: Int = 0
    assert(x.<((1, 2)))
  }

  test("Integers must have > to check if a number is bigger than the upper range boundaries") {
    val x: Int = 3
    assert(x.>((1, 2)))
  }

  test("Method applyTwoOperations apply two operations between an integer and a value"){
    val x: Int = 3
    val value: Int = 2
    assert(x.applyTwoOperations(value)(_ + _)(_ * _) == (x + value, x * value))
  }

}
