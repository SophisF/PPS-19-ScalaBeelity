package scala.model.helper

import breeze.linalg._

/**
 * Pimping breeze.Matrix adding utility functions used in Gaussian filter calc.
 * TODO change to AnyVal ?
 *
 * @author Paolo Baldini
 */
object MatrixHelper {

  type Matrix[T] = DenseMatrix[T] // TODO ?
  implicit class RichMatrix[T](matrix: Matrix[Double]) {

    /**
     * Mirror matrix on horizontal axis
     *
     * @param mirrorCenter flip also center column or drop it
     * @return mirrored matrix
     */
    def mirrorX(mirrorCenter: Boolean = true): Matrix[Double] =
      DenseMatrix.horzcat(matrix.flipX(mirrorCenter), matrix)

    /**
     * Mirror matrix on vertical axis
     *
     * @param mirrorCenter flip also center row or drop it
     * @return mirrored matrix
     */
    def mirrorY(mirrorCenter: Boolean = true): Matrix[Double] =
      DenseMatrix.vertcat(matrix.flipY(mirrorCenter), matrix)

    /**
     * Flip matrix on horizontal axis
     *
     * @param mirrorCenter flip also center column or drop it
     * @return flipped matrix
     */
    def flipX(mirrorCenter: Boolean = true): Matrix[Double] = new DenseMatrix(matrix.rows,
      if (mirrorCenter) matrix.cols else matrix.cols -1, matrix.data.grouped(matrix.rows)
        .drop(if (mirrorCenter) 0 else 1).reduce((_1, _2) => _2.appendedAll(_1)))

    /**
     * Flip matrix on vertical axis
     *
     * @param mirrorCenter flip also center row or drop it
     * @return flipped matrix
     */
    def flipY(mirrorCenter: Boolean = true): Matrix[Double] =
      new DenseMatrix(if (mirrorCenter) matrix.rows else matrix.rows -1, matrix.cols, matrix.data.grouped(matrix.rows)
        .map(_.drop(if (mirrorCenter) 0 else 1).reverse).reduce((_1, _2) => _1.appendedAll(_2)))
  }
}
