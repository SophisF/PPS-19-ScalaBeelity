package scala.model.environment.utility

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
    def mirror(mirrorCenter: Boolean = true): Iterator[A] = iterator.duplicate.productIterator
      .map(_.asInstanceOf[Iterator[A]]).reduce((_1, _2) => _1 ++ _2.toSeq.reverse.drop(if (mirrorCenter) 0 else 1))
  }

  implicit class ConditionalIterator[A](iterator: Iterator[A]) {
    private var next: Option[A] = iterator.nextOption()

    def conditionalNext(condition: A => Boolean): Option[A] = next match {
      case Some(a) if condition(a) => next = iterator nextOption; Option(a)
      case _ => Option empty
    }
  }

  implicit def toIterator[A](iterable: Iterable[A]): Iterator[A] = iterable.iterator
}
