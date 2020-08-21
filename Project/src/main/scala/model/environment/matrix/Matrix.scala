package scala.model.environment.matrix

import breeze.linalg._
import breeze.math.Ring.ringFromField

import scala.collection.parallel.CollectionConverters._

/**
 * Pimping breeze.Matrix adding utility functions.
 *
 * @author Paolo Baldini
 */
object Matrix {
  type Matrix[T] = DenseMatrix[T]

  implicit class DroppableMatrix[T](matrix: Matrix[T]) {

    /**
     * Drop a percentage of columns in a balanced way (not all from start/end but in a distributed way).
     *
     * @param dropN percentage to drop. Value should be between 0 and 1
     * @return matrix with 1-`dropN` percent of columns
     */
    def dropColumns(dropN: Double): Matrix[T] = matrix.delete(Iterable.iterate(0, ((matrix.cols -1) * dropN).toInt)
    (_ + Math.ceil(1 / dropN).toInt).takeWhile(_ < matrix.cols).toSeq, Axis._1)

    /**
     * Drop a percentage of rows in a balanced way (not all from start/end but in a distributed way).
     *
     * @param dropN percentage to drop. Value should be between 0 and 1
     * @return matrix with 1-`dropN` percent of rows
     */
    def dropRows(dropN: Double): Matrix[T] = matrix.delete(Iterable.iterate(0, ((matrix.rows -1) * dropN).toInt)
    (_ + Math.ceil(1 / dropN).toInt).takeWhile(_ < matrix.rows).toSeq, Axis._0)

    // TODO move in trasformable after adjusted double problem
    //def parallelMap[R](op: T => R): Matrix[R] = DenseMatrix.create[R](matrix.rows, matrix.cols, matrix.data.par.map(op)
    //  .toArray)(null)
  }

  implicit class TransformableMatrix[T](matrix: Matrix[Double]) { // TODO double

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
      if (mirrorCenter) matrix.cols else matrix.cols - 1, matrix.data.grouped(matrix.rows)
        .drop(if (mirrorCenter) 0 else 1).reduce((_1, _2) => _2.appendedAll(_1)))

    /**
     * Flip matrix on vertical axis
     *
     * @param mirrorCenter flip also center row or drop it
     * @return flipped matrix
     */
    def flipY(mirrorCenter: Boolean = true): Matrix[Double] =
      new DenseMatrix(if (mirrorCenter) matrix.rows else matrix.rows - 1, matrix.cols, matrix.data.grouped(matrix.rows)
        .map(_.drop(if (mirrorCenter) 0 else 1).reverse).reduce((_1, _2) => _1.appendedAll(_2)))
  }
}