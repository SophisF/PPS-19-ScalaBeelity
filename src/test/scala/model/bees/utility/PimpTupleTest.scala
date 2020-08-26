package model.bees.utility

import org.scalatest.funsuite.AnyFunSuite
import scala.model.bees.utility.PimpTuple._

class PimpTupleTest extends AnyFunSuite{
  private val tuple1: (Int, Int) = (9, 11)
  private val tuple2: (Int, Int) = (3, 7)
  private val tuple3: (Int, Int) = (10, 12)

  test("Two tuple should intersect each other if their value are overlapped."){
    assert(tuple1.intersection(tuple3) == 2)
  }

  test("Two tuple should not intersect each other if their value aren't overlapped."){
    assert(tuple1.intersection(tuple2) == 0)
  }

}
