package scala.utility

import scala.util.Random

import Tuple.RichTuple

/**
 * Add utility methods to Iterable
 *
 * @author Paolo Baldini
 */
object IterableHelper {

  implicit class RichIterable[A](iterable: Iterable[A]) {

    /**
     * Mirror an iterable. E.g.: 1,2,3,4 -> 1,2,3,4,4,3,2,1
     *
     * @param mirrorCenter if false do not duplicate center element
     * @return the mirrored iterator
     */
    def mirror(mirrorCenter: Boolean = true): Iterable[A] =
      iterable.duplicate.reduce(_ ++ _.reverse.drop(if (mirrorCenter) 0 else 1)) to Iterable

    def reverse: Iterable[A] = iterable.reverseIterator to Iterable

    def random: Option[A] = Option.when(iterable nonEmpty)(iterable(Random nextInt iterable.size))
  }

  implicit def iteratorOf[A](iterable: Iterable[A]): Iterator[A] = iterable iterator

  implicit def iterableOf[A](iterator: Iterator[A]): Iterable[A] = iterator to Iterable

  implicit def sequenceOf[A](iterable: Iterable[A]): Seq[A] = iterable toSeq

  implicit def sequenceOf[A](iterator: Iterator[A]): Seq[A] = iterator toSeq
}
