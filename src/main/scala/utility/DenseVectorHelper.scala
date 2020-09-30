package scala.utility

import breeze.linalg.DenseVector
import spire.ClassTag

/**
 * Dense vector helper.
 */
object DenseVectorHelper {

  /**
   * Conversion to Dense Vector.
   *
   * @param iterable to convert
   * @tparam T , type of data
   * @return dense vector
   */
  implicit def toDenseVector[T](iterable: Iterable[T])(implicit classTag: ClassTag[T]): DenseVector[T] =
    DenseVector(iterable toArray)
}
