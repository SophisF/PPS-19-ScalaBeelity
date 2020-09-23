package scala.model.utility

import org.scalatest.funsuite.AnyFunSuite

import scala.utility.PimpTuple._

class PimpTupleTest extends AnyFunSuite {

  type Range = (Int, Int)

  private val tuple1: Range = (9, 11)
  private val tuple2: Range = (3, 7)
  private val tuple3: Range = (10, 12)

  test("One Range should contains another if the first value of the second is between the two values of the first.") {
    assert(tuple1.contains(tuple3))
  }

  test("One Range should not contains another if the first value of the second is not between the two values of the first.") {
    assert(!tuple2.contains(tuple3))
  }

  test("The average of a Range is the mean of its values") {
    assert(tuple1.average == (tuple1._1 + tuple1._2) / 2)
  }
}
