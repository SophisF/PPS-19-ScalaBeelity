package scala.model.environment.utility

import breeze.linalg.DenseVector
import spire.ClassTag

object DenseVectorHelper {

  implicit def toDenseVector[T](iterable: Iterable[T])(implicit classTag: ClassTag[T]): DenseVector[T] =
    DenseVector(iterable toArray)
}
