package scala.model.environment.property

import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.numerics.exp

import scala.model.helper.IteratorHelper.RichIterator
import scala.model.environment.matrix.Matrix.TransformableMatrix

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
   * @param peak          of the function (highest point)
   * @param stop          limit. When lesser values than this are found, stop take them (used to not continue infinitely)
   * @param decrementRate influences the 'width' of the function. The greater it is, the more the values descent slowly
   * @return the right side (from the center) of a 2d gaussian curve
   */
  def positive2dGaussianFunction(peak: Int, stop: Int = 1, decrementRate: Int = 1): Iterator[Double] =
    peak * stop match {
      case signedResult if signedResult < 0 => Iterator.empty
      case _ => Iterator.iterate(0)(it => it + 1)
        .map(it => peak * exp((it * it) / -(2.0 * decrementRate * decrementRate))).takeWhile(it => it.abs >= stop.abs)
    }

  /**
   * Calculate influence descent through use of a gaussian function (See
   * [[https://en.wikipedia.org/wiki/Gaussian_function]]).
   * The `b` value of the function is considered 0 and thus not passed.
   *
   * @param peak          or center-value of the curve,
   * @param stop          value during the descent (i.e., stop when value became less than _)
   * @param decrementRate influences the 'width' of the function. The greater it is, the more the values descent slowly
   * @return the 2d gaussian function
   */
  def gaussianFunction2d(peak: Int, stop: Int = 1, decrementRate: Int = 1): Iterator[Double] =
    positive2dGaussianFunction(peak, stop, decrementRate).toArray.reverseIterator.mirror(false)

  /**
   * Build a matrix representing the 3d gaussian function
   *
   * @param peak   of the function (highest point)
   * @param stop   limit. When lesser values than this are found, stop take them (used to not continue infinitely).
   *               Filter applied only on the main axes
   * @param width  Influences the width of the function/filter.
   *               The greater it is, the largest the filter become (values descent slowly)
   * @param height Influences the height of the function/filter.
   *               The greater it is, the higher the filter become (values descent slowly)
   * @return the 3d gaussian filter
   */
  def gaussianFunction3d(peak: Int, stop: Int = 1, width: Int = 1, height: Int = 1)
  : DenseMatrix[Double] = peak match {
    case peak if peak < -1 || peak > 1 => (
      DenseVector(positive2dGaussianFunction(peak, stop, height) toArray) *
        DenseVector(positive2dGaussianFunction(peak, stop, width) map (_ / peak) toArray).t
      ).mirrorX(false).mirrorY(false)
    case _ => DenseMatrix.create(0, 0, Array.empty)
  }


  //  case class GuassianFilter ()
  //
  //  object GuassianFilter {
  //
  //  }

  //  def makeGaussianFunction(
  //                            peak: Int,
  //                            stop: Int,
  //                            dimension: Int*,
  //                          ): Either[Iterator[Double], DenseMatrix[Double]] = {
  //    if(dimension.length > 2) gaussianFunction3d(peak, stop, dimension.head, dimension.last) else gaussianFunction2d(peak, stop, dimension)
  //  }


}