package scala.utility

/**
 * Utility class to pimp the iterables.
 */
object PimpIterable {

  /**
   * Implicit class for the iterables of int.
   * @param iterable the iterable of int.
   */
  implicit class MyIterable(iterable: Iterable[Int]){
    def average: Int = iterable.sum / iterable.size
  }

}
