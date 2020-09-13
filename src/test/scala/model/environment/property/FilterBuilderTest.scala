package scala.model.environment.property

import org.scalatest.funsuite.AnyFunSuite

import scala.model.environment.property.FilterBuilder._

/**
 * Test class for the creation of the gaussian filters
 *
 * @author Paolo Baldini, Enrico Gnagnarella
 */
class FilterBuilderTest extends AnyFunSuite {
  private val GF2P = "Positive 2d Gaussian Function"
  private val GF2 = "2d Gaussian Function"
  private val GF3 = "3d Gaussian Function"

  test(s"$GF2P with different sign of peak and stop parameters should return empty function") {
    val result = positive2dGaussianFunction(1, -1) :: positive2dGaussianFunction(-1) :: Nil

    assert(result forall(_ isEmpty))
  }

  test(s"$GF2P with 0 stop parameter should return a function without problems") {
    val result = positive2dGaussianFunction(1, 0) :: positive2dGaussianFunction(-1, 0) :: Nil

    assert(result forall(_ nonEmpty))
  }

  test(s"$GF2P with positive or negative decrementRate parameter (x, y -> x.abs == y.abs) should return " +
  "same result") {
    val result1 = positive2dGaussianFunction(50, 1, 5)
    val result2 = positive2dGaussianFunction(50, 1, -5)

    assert(result1 sameElements result2)
  }

  test(s"$GF2P with 0 decrementRate parameter should not return any filter") {
    val result = positive2dGaussianFunction(50, 1, 0)

    assert(result isEmpty)
  }

  test(s"$GF2P should return 'descending' values") {
    val result = positive2dGaussianFunction(-50) map(_.abs.toInt)

    assert(result.foldLeft(true, Int.MaxValue)((previousEvaluation, value) =>
      (previousEvaluation._2 > value && previousEvaluation._1, value)) _1)
  }

  test(s"$GF2P with specific parameters should return a fixed sequence") {
    val result = positive2dGaussianFunction(50) map(_ toInt)

    assert(result sameElements 50 :: 30 :: 6 :: Nil)
  }

  test(s"$GF2P should not return values below to the stop one") {
    val result = positive2dGaussianFunction(50, 1, 70).map(_.toInt)
    assert(result forall(_ >= 1))
  }

  test(s"$GF2 should always return an odd number of values") {
    val result = gaussianFunction2d(50, 1, 10)
    assert(result.size % 2 > 0)
  }

  test(s"$GF2 should always return a center-mirrored filter") {
    val result = gaussianFunction2d(50, 1, 10)
    assert(result.take(result.size -1 / 2) sameElements result.drop(result.size -1 / 2 +1).toArray.reverse)
  }

  test(s"$GF3 with specific parameters should return a fixed sequence") {
    val result = gaussianFunction3d(50).map(_.toInt).data

    assert(result sameElements Array(
      0, 4, 6, 4, 0,
      4, 18, 30, 18, 4,
      6, 30, 50, 30, 6,
      4, 18, 30, 18, 4,
      0, 4, 6, 4, 0)
    )
  }
}
