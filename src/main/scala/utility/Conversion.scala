package scala.utility

/**
 * Utilities for implicit conversion of data.
 *
 */
object Conversion {

  implicit def iteratorOf[A](iterable: Iterable[A]): Iterator[A] = iterable iterator

  implicit def iterableOf[A](iterator: Iterator[A]): Iterable[A] = iterator to Iterable

  implicit def mapOf[K, V](iterable: Iterable[(K, V)]): Map[K, V] = iterable toMap

  implicit def mapOf[K, V](iterator: Iterator[(K, V)]): Map[K, V] = iterator toMap

  implicit def sequenceOf[A](iterable: Iterable[A]): Seq[A] = iterable toSeq

  implicit def sequenceOf[A](iterator: Iterator[A]): Seq[A] = iterator toSeq
}
