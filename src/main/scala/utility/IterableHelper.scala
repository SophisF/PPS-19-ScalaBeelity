package scala.utility

import scala.util.Random

/**
 * Add utility methods to Iterable
 *
 * @author Paolo Baldini
 */
object IterableHelper {

  implicit class RichIterable[A](iterable: Iterable[A]) {

    def random: Option[A] = Option.when(iterable nonEmpty)(iterable(Random nextInt iterable.size))
  }

  implicit def toSeq[A](iterable: Iterable[A]): Seq[A] = iterable.toSeq
}
