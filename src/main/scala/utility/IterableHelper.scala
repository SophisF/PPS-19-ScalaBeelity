package scala.utility

import scala.util.Random
import Tuple.RichTuple
import scala.utility.Conversion.{iteratorOf, sequenceOf}

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
     * @return the mirrored iterable
     */
    def mirror(mirrorCenter: Boolean = true): Iterable[A] =
      iterable.duplicate.reduce(_ ++ _.reverse.drop(if (mirrorCenter) 0 else 1)) to Iterable

    /**
     * Reverse an iterable. E.g.: 1,2,3,4 -> 4,3,2,1
     *
     * @return the reversed iterable
     */
    def reverse: Iterable[A] = iterable.reverseIterator to Iterable

    /**
     * Get an optional random element from an iterable.
     *
     * @return None if the iterable is empty, a Some(element) otherwise
     */
    def random: Option[A] = Option.when(iterable nonEmpty)(iterable(Random nextInt iterable.size))
  }
}
