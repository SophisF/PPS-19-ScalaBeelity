package scala.model.environment.property

import org.scalatest.funsuite.AnyFunSuite

import scala.model.environment.property.GaussianFilterBuilder._

/**
 * Test class for the creation of gaussian filters
 *
 * @author Paolo Baldini, Enrico Gnagnarella
 */
class GaussianFilterBuilderTest extends AnyFunSuite {
  private val GF2P = "Positive 2d Gaussian Function"
  private val GF2 = "2d Gaussian Function"
  private val GF3 = "3d Gaussian Function"

  /********************************************************************************************************************
   * POSITIVE 2D GAUSSIAN FILTER
   ********************************************************************************************************************/

  test(s"$GF2P with positive peak should return a descendant sequence of elements") {
    val result = function2dOneSided(10).toList
    assert(result.sorted((x: Double, y: Double) => y - x toInt).iterator sameElements result)
  }

  test(s"$GF2P with negative peak should return an ascendant sequence of elements") {
    val result = function2dOneSided(-10, -1).toList
    assert(result.sorted.iterator sameElements result)
  }

  test(s"$GF2P with positive peak and negative stop should return a descendant sequence of elements") {
    val result = function2dOneSided(10, -10).toList
    assert(result.sorted((x: Double, y: Double) => y - x toInt).iterator sameElements result)
  }

  test(s"$GF2P with negative peak and positive stop should return an ascendant sequence of elements") {
    val result = function2dOneSided(-10, 10).toList
    assert(result.sorted.iterator sameElements result)
  }

  test(s"$GF2P with positive peak should have a maximum value equals to it") {
    val result = function2dOneSided(10).toList
    assert(result.max == 10)
  }

  test(s"$GF2P with negative peak should have a minimum value equals to it") {
    val result = function2dOneSided(-10, -1).toList
    assert(result.min == -10)
  }

  test(s"$GF2P with descendant values should not return values lower than stop") {
    val result = function2dOneSided(10, -10).toList
    assert(result.min >= -10)
  }

  test(s"$GF2P with ascendant values should not return values greater than stop") {
    val result = function2dOneSided(-10, 10).toList
    assert(result.max <= 10)
  }

  test(s"$GF2P with 0 stop parameter should return a function without problems") {
    val result = function2dOneSided(10, 0) :: function2dOneSided(-10, 0) :: Nil
    assert(result forall(_ nonEmpty))
  }

  test(s"$GF2P with same absolute decrementRate value should return same the result") {
    assert(function2dOneSided(50, 1, 5).iterator sameElements function2dOneSided(50, 1, -5).iterator)
  }

  test(s"$GF2P with 0 decrementRate parameter should not return any filter") {
    assert(function2dOneSided(10, -10, 0) isEmpty)
  }

  test(s"$GF2P with specific parameters should return a fixed sequence") {
    val result = function2dOneSided(50) map(_ toInt)

    assert(result.iterator sameElements 50 :: 30 :: 6 :: Nil)
  }

  /********************************************************************************************************************
   * 2D GAUSSIAN FILTER
   ********************************************************************************************************************/

  test(s"$GF2 with positive peak should have at least a value equal to it") {
    val result = function2d(10, -10)
    assert(result.max == 10)
  }

  test(s"$GF2 with negative peak should have at least a value equal to it") {
    val result = function2d(-10, 10)
    assert(result.min == -10)
  }

  test(s"$GF2 with descendant values should not return values lower than stop") {
    val result = function2d(10, -10).toList
    assert(result.min >= -10)
  }

  test(s"$GF2 with ascendant values should not return values greater than stop") {
    val result = function2d(-10, 10).toList
    assert(result.max <= 10)
  }

  test(s"$GF2 should always return an odd number of values") {
    val result = function2d(50, 1, 10)
    assert(result.size % 2 > 0)
  }

  test(s"$GF2 should always return a center-mirrored filter") {
    val result = function2d(50, 1, 10)
    assert(result.take(result.size / 2).iterator sameElements result.drop(result.size / 2 +1).toArray.reverse)
  }

  /********************************************************************************************************************
   * 3D GAUSSIAN FILTER
   ********************************************************************************************************************/

  test(s"$GF3 with positive peak should have at least a value equal to it") {
    val result = function3d(10, -10)
    assert(result.data.max == 10)
  }

  test(s"$GF3 with negative peak should have at least a value equal to it") {
    val result = function3d(-10, 10)
    assert(result.data.min == -10)
  }

  test(s"$GF3 with specific parameters should return a fixed sequence") {
    val result = function3d(50).map(_ toInt).data

    assert(result sameElements Array(
      0, 4, 6, 4, 0,
      4, 18, 30, 18, 4,
      6, 30, 50, 30, 6,
      4, 18, 30, 18, 4,
      0, 4, 6, 4, 0
    ))
  }
}
