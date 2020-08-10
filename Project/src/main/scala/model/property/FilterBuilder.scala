package scala.model.property

import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.numerics.exp

import scala.model.helper.IteratorHelper.RichIterator
import scala.model.matrix.Matrix.TransformableMatrix

/**
 * TODO chose a decent name.
 * Contains functions to create gaussian filters.
 *
 * @author Paolo Baldini
 */
object FilterBuilder {

  /**
   * Build an 'half' 2d gaussian filter
   *
   * @param peak of the function (highest point)
   * @param stop when lesser values than this are found (used to not continue infinitely)
   * @param width Influences the width of the function/filter. The greater it is, the largest the filter become (values descent slowly)
   * @return the half 2d filter
   */
  def twoDimensionalPositiveDescent(peak: Int, stop: Int = 1, width: Double = 1): Iterator[Double] = Iterator
    .iterate(0)(it => it + 1).map(it => peak * exp((it * it) / -(2 * width * width)))
    .takeWhile(it => (peak > 0 && it > stop) || (peak < 0 && it < stop))

  /**
   * Calculate influence descent through use of a gaussian function (See [[https://en.wikipedia.org/wiki/Gaussian_function]]).
   * The `b` value of the function is considered 0 and thus not passed.
   *
   * @param peak or center-value of the curve,
   * @param stop value during the descent (i.e., stop when value became less than _)
   * @return the 2d filter
   */
  def twoDimensionalDescent(peak: Int, stop: Int = 1, width: Double = 1): Iterator[Double] =
    twoDimensionalPositiveDescent(peak, stop, width).toArray.reverseIterator.mirror(false)

  /**
   * Build a matrix representing the 3d gaussian function
   *
   * @param peak of the function (highest point)
   * @param stop when lesser values than this are found (used to not continue infinitely)
   * @param width Influences the width of the function/filter. The greater it is, the largest the filter become (values descent slowly)
   * @param height Influences the height of the function/filter. The greater it is, the higher the filter become (values descent slowly)
   * @return the 3d filter
   */
  def threeDimensionalPositiveDescent(peak: Int, stop: Int = 1, width: Double = 1, height: Double = 1)
  : DenseMatrix[Double] = peak match {
    case peak if peak < -1 || peak > 1 => (DenseVector(twoDimensionalPositiveDescent(peak, stop, height).toArray) *
      DenseVector(twoDimensionalPositiveDescent(peak, stop, width).toArray.map(_ / peak)).t)
      .mirrorX(false).mirrorY(false)
    case _ => DenseMatrix.create(0, 0, Array())
  }
}