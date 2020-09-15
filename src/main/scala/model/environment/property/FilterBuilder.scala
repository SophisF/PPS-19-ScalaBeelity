package scala.model.environment.property

import breeze.linalg.DenseMatrix
import breeze.numerics.exp

import scala.model.environment.utility.IteratorHelper.RichIterator
import scala.model.environment.utility.DenseVectorHelper._
import scala.model.environment.utility.DenseMatrixHelper.{TransformableMatrix, empty}

/**
 * TODO maybe rename to GaussianFilterBuilder.<br>
 * Contains functions to create gaussian filters.
 *
 * @author Paolo Baldini
 */
object FilterBuilder {

  /**
   * Build an 'half' (only values at right of the center) 2d gaussian filter
   *
   * @param peak of the function (highest point)
   * @param stop limit. When lesser values than this are found, stop take them (used to not continue infinitely)
   * @param decrementRate influences the 'width' of the function. The greater it is, the more the values descent slowly
   * @return the right side (from the center) of a 2d gaussian curve
   */
  def positive2dGaussianFunction(peak: Int, stop: Int = 1, decrementRate: Int = 1): Iterable[Double] = {
    val ranges = correctRanges(peak, stop)
    readjustRanges(peak, stop, Iterator.iterate(0)(_ + 1)
      .map(it => ranges._1 * exp( (it * it) / -(2.0 * decrementRate * decrementRate) ))
      .takeWhile(_.abs >= ranges._2.abs).toArray)
  }

  /**
   * Calculate influence descent through use of a gaussian function (See
   * [[https://en.wikipedia.org/wiki/Gaussian_function]]).
   * The `b` value of the function is considered 0 and thus not passed.
   *
   * @param peak or center-value of the curve,
   * @param stop value during the descent (i.e., stop when value became less than _)
   * @param decrementRate influences the 'width' of the function. The greater it is, the more the values descent slowly
   * @return the 2d gaussian function
   */
  def gaussianFunction2d(peak: Int, stop: Int = 1, decrementRate: Int = 1): Iterable[Double] =
    positive2dGaussianFunction(peak, stop, decrementRate).toArray.reverseIterator.mirror(false).toArray

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
  def gaussianFunction3d(peak: Int, stop: Int = 1, width: Int = 1, height: Int = 1): DenseMatrix[Double] =
    correctRanges(peak, stop) match {
      case (peak, _) if peak.abs == 0 => empty
      case (p, s) => (positive2dGaussianFunction(p, s, height) * positive2dGaussianFunction(p, s, width).map(_ / p).t)
        .map(_ + stop - s).mirrorX(mirrorCenter = false).mirrorY(mirrorCenter = false)
    }

  def correctRanges(peak: Int, stop: Int): (Int, Int) =
    concordantSign(peak, stop, (_1, _2) => (_1 - _2, 0), (_1, _2) => (_1, _2)) match {
      case t if t._2 == 0 => (t._1 + math.signum(t._1) * 1, t._2 + math.signum(t._1) * 1)
      case t => t
    }

  def readjustRanges(peak: Int, stop: Int, iterator: Iterable[Double]): Iterable[Double] = {
    val t = correctRanges(peak, stop)
    iterator.map(_ + stop - t._2)
  }

  def concordantSign[T](value1: Int, value2: Int, discordant: (Int, Int) => T, concordant: (Int, Int) => T): T =
    value1 * value2 match {
      case signedResult if signedResult < 0 => discordant(value1, value2)
      case _ => concordant(value1, value2)
    }
}