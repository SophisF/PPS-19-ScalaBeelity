package scala.utility

/**
 * A pimp library to add some methods to Int class.
 */
object PimpInt {

  /**
   * Wrapper and conversion with implicit class.
   *
   * @param number int value.
   */
  implicit class MyInt(number: Int) {

    type Range = (Int, Int)

    /**
     * Method to check if the integer value is in a range.
     *
     * @param tuple the range values.
     * @return true if the number is in the range, false otherwise.
     */
    def in(tuple: Range): Boolean = number >= tuple._1 && number <= tuple._2

    /**
     * Method to check if the integer value is lower than a range.
     *
     * @param tuple the range values.
     * @return true if the number is lower than the range, false otherwise.
     */
    def <(tuple: Range): Boolean = number < tuple._1

    /**
     * Method to check if the integer value is greater than a range.
     *
     * @param tuple the range values.
     * @return true if the number is greater than the range, false otherwise.
     */
    def >(tuple: Range): Boolean = number > tuple._2

    /**
     * Method to convert an integer to a value of influence.
     * @return the influence value.
     */
    def toInfluenceValue: Double = number.toDouble

    /**
     * Method to apply two operations between an integer and another integer.
     * @param value the another integer.
     * @param operation1 the first operation.
     * @param operation2 the second operation.
     * @return a tuple with the result of the two operations.
     */
    def applyTwoOperations(value: Int)(operation1: (Int, Int) => Int)(operation2: (Int, Int) => Int): (Int, Int) = {
      (operation1(number, value), operation2(number, value))
    }
  }

}
