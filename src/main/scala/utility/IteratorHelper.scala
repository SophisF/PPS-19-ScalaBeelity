package scala.utility

import Tuple.RichTuple

/**
 * Applying `Pimp my library` pattern to iterable to add utilities methods
 *
 * @author Paolo Baldini
 */
object IteratorHelper {

  implicit class RichIterator[A](iterator: Iterator[A]) {

    /**
     * Mirror an iterator. E.g.: 1,2,3,4 -> 1,2,3,4,4,3,2,1
     *
     * @param mirrorCenter if false do not duplicate center element
     * @return the mirrored iterator
     */
    def mirror(mirrorCenter: Boolean = true): Iterator[A] = iterator.duplicate
      .reduce(_ ++ _.reverse.drop(if (mirrorCenter) 0 else 1))
  }

  implicit def toIterator[A](iterable: Iterable[A]): Iterator[A] = iterable iterator

  implicit def toIterable[A](iterator: Iterator[A]): Iterable[A] = iterator to Iterable

  implicit def toSeq[A](iterator: Iterator[A]): Seq[A] = iterator toSeq
}
