package scala.model.bees.utility

/**
 * A pimp library to add some methods to Tuple class.
 */
object PimpTuple {

  type Range = (Int, Int)

  /**
   * An implicit class to pimp tuples of integer.
   * @param tuple the (Int, Int) tuple.
   */
  implicit class MyTuple(tuple: Range) {

    /**
     * Method to check if the range contains not strictly another range.
     * @param other the other range.
     * @return a boolean value, true if contains, false otherwise.
     */
    def contains(other: Range): Boolean = {
      tuple._1 <= other._1 && tuple._2 >= other._1
    }

    /**
     * Method to calculate the average value between a range.
     * @return
     */
    def average: Int = ((tuple._1 + tuple._2).toDouble / 2).toInt
  }

}
