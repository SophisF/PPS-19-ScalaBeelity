package scala.model.environment.property

import math.pow

import breeze.linalg.DenseMatrix
import breeze.numerics.exp

import scala.utility.Tuple._
import scala.utility.DenseVectorHelper._
import scala.utility.DenseMatrixHelper.{TransformableMatrix, empty}
import scala.utility.IterableHelper.RichIterable

/** Contains functions to create gaussian filters */
private[property] object GaussianFilterBuilder {

  /**
   * Build an 'half' (only values descending from ceil to floor or vice versa ascending) 2d gaussian filter
   *
   * @param peak of the function (highest point)
   * @param stop limit. When lesser values than this are found, stop take them (used to not continue infinitely)
   * @param decrementRate influences the 'width' of the function. The greater it is, the more the values descent slowly
   * @return an ascending/descending 2d gaussian curve
   */
  def function2dOneSided(peak: Int, stop: Int = 1, decrementRate: Int = 1): Iterable[Double] = correctRanges(peak, stop)
    .map((_peak, _stop) => readjustRanges(peak, stop, Iterator.from(0).map(pow(_, 2) / -(2.0 * pow(decrementRate, 2)))
      .map(_peak * exp(_)).takeWhile(_.abs >= _stop.abs) to Iterable))

  /**
   * Calculate influence descent through use of a gaussian function (See
   * [[https://en.wikipedia.org/wiki/Gaussian_function]]).
   * The `b` value of the function is considered 0 and thus not passed.
   *
   * @param peak or center-value of the curve (highest point),
   * @param stop value during the descent (i.e., stop when value became less than it)
   * @param decrementRate influences the 'width' of the function. The greater it is, the more the values descent slowly
   * @return the 2d gaussian function
   */
  def function2d(peak: Int, stop: Int = 1, decrementRate: Int = 1): Iterable[Double] =
    function2dOneSided(peak, stop, decrementRate).reverse.mirror(false)

  /**
   * Build a matrix representing the 3d gaussian function
   *
   * @param peak of the function (highest point)
   * @param stop limit. When lesser values than this are found, stop take them (used to not continue infinitely).
   *             Filter applied only on the main axes
   * @param width Influences the width of the function/filter.
   *              The greater it is, the largest the filter become (values descent slowly)
   * @param height Influences the height of the function/filter.
   *               The greater it is, the higher the filter become (values descent slowly)
   * @return the 3d gaussian filter
   */
  def function3d(peak: Int, stop: Int = 1, width: Int = 1, height: Int = 1): DenseMatrix[Double] =
    correctRanges(peak, stop) match {
      case (peak, _) if peak.abs == 0 => empty
      case (p, s) => (function2dOneSided(p, s, height) * function2dOneSided(p, s, width).map(_ / p).t)
        .map(_ + stop - s).mirrorX(mirrorCenter = false).mirrorY(mirrorCenter = false)
    }

  /**
   * The gaussian function is 'sensitive' to the passed values, so it is necessary to pass proper values of peak an stop
   *
   * @param peak the highest value we want to obtain in the output filter
   * @param stop the lowest value we want to obtain in the output filter
   * @return a couple of values that can be used to safely calculate the filter.
   *         After that will be necessarily to re-range the output values
   */
  private def correctRanges(peak: Int, stop: Int): (Int, Int) =
    concordantSign(peak, stop, (_1, _2) => (_1 - _2, 0), (_1, _2) => (_1, _2)) match {
      case t if t._2 == 0 => (t._1 + math.signum(t._1) * 1, t._2 + math.signum(t._1) * 1)
      case t => t
    }

  /**
   * If the input parameters has been modified by the 'correctRanges' function,
   * readjust them to return the required filter
   *
   * @param peak the highest value we want to obtain in the output filter
   * @param stop the lowest value we want to obtain in the output filter
   * @param iterator the sequence of values to re-adjust
   * @return the readjusted sequence of values
   */
  private def readjustRanges(peak: Int, stop: Int, iterator: Iterable[Double]): Iterable[Double] =
    iterator.map(_ + stop - correctRanges(peak, stop)._2)

  /**
   * Utility function that exec a function if the sign of two number are concordant or another one otherwise.
   *
   * @param value1 the first value
   * @param value2 the second value
   * @param discordant the function to exec if the sign are discordant. Takes the two values as input
   * @param concordant the function to exec if the sign are concordant. Takes the two values as input
   * @tparam T return type of the functions
   * @return the result of the correct operation
   */
  private def concordantSign[T](value1: Int, value2: Int, discordant: (Int, Int) => T, concordant: (Int, Int) => T): T =
    value1 * value2 match {
      case signedResult if signedResult < 0 => discordant(value1, value2)
      case _ => concordant(value1, value2)
    }
}