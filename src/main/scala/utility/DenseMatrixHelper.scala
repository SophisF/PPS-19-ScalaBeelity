package scala.utility

import breeze.linalg._
import breeze.storage.Zero

import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.mutable.ParIterable
import scala.reflect.ClassTag

/**
 * Pimping breeze.Matrix adding utility functions.
 */
object DenseMatrixHelper {
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
  }

  implicit class TransformableMatrix[T](matrix: Matrix[T]) {

    /**
     * Mirror matrix on horizontal axis
     *
     * @param mirrorCenter flip also center column or drop it
     * @return mirrored matrix
     */
    def mirrorX(mirrorCenter: Boolean = true)(implicit classTag: ClassTag[T], zero: Zero[T]): Matrix[T] =
      matrix.cols match {
        case 1 if mirrorCenter => create(2, matrix.data.map(it => it :: it :: Nil).reduce((_1, _2) => _1 appendedAll _2))
        case 1 => matrix
        case _ => DenseMatrix.horzcat(matrix.flipX(mirrorCenter), matrix)
      }

    /**
     * Mirror matrix on vertical axis
     *
     * @param mirrorCenter flip also center row or drop it
     * @return mirrored matrix
     */
    def mirrorY(mirrorCenter: Boolean = true)(implicit classTag: ClassTag[T], z: Zero[T]): Matrix[T] =
      matrix.rows match {
        case 1 if mirrorCenter => create(matrix.cols, matrix.data.appendedAll(matrix.data))
        case 1 => matrix
        case _ => DenseMatrix.vertcat(matrix.flipY(mirrorCenter), matrix)
      }

    /**
     * Flip matrix on horizontal axis
     *
     * @param mirrorCenter flip also center column or drop it
     * @return flipped matrix
     */
    def flipX(mirrorCenter: Boolean = true)(implicit classTag: ClassTag[T], z: Zero[T]): Matrix[T] = matrix.cols match {
      case 1 => if (mirrorCenter) matrix else empty
      case _ => DenseMatrix.create(matrix.rows, matrix.cols - (if (mirrorCenter) 0 else 1),
        matrix.data.grouped(matrix.rows).drop(if (mirrorCenter) 0 else 1).reduce((_1, _2) => _2.appendedAll(_1)))
    }

    /**
     * Flip matrix on vertical axis
     *
     * @param mirrorCenter flip also center row or drop it
     * @return flipped matrix
     */
    def flipY(mirrorCenter: Boolean = true)(implicit classTag: ClassTag[T], z: Zero[T]): Matrix[T] = matrix.rows match {
      case 1 if mirrorCenter => matrix
      case 1 => empty
      case _ => DenseMatrix.create(matrix.rows - (if (mirrorCenter) 0 else 1), matrix.cols, matrix.data
        .grouped(matrix.rows).map(_.drop(if (mirrorCenter) 0 else 1).reverse).reduce((f, s) => f appendedAll s))
    }
  }

  implicit class ParallelMatrix[T](matrix: Matrix[T]) {

    def parallelMap[R: ClassTag](op: T => R)(implicit zero: Zero[R]): Matrix[R] =
      create(matrix cols, matrix.data.par.map(op))

    def indexedParallelMap[R: ClassTag](op: (T, Int) => R)(implicit zero: Zero[R]): Matrix[R] =
      create(matrix cols, matrix.data.par.zipWithIndex.map[R](it => op(it._1, it._2)))
  }

  implicit def toArray[T: ClassTag](iterable: ParIterable[T]): Iterable[T] = iterable.seq

  def empty[T: ClassTag]()(implicit zero: Zero[T]): Matrix[T] = DenseMatrix.create[T](0, 0, Array empty)

  def create[T: ClassTag](cols: Int, iterable: Iterable[T])(implicit zero: Zero[T]): DenseMatrix[T] =
    DenseMatrix.create(iterable.size / cols, cols, iterable toArray)
}