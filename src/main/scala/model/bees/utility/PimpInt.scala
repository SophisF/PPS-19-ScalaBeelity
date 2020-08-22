package scala.model.bees.utility

/**
 * A pimp library to add some methods to Int class.
 */
object PimpInt {

  /**
   * Wrapper and conversion with implicit class.
   * @param number int value.
   */
  implicit class MyInt(number: Int) {
    def in(tuple: (Int, Int)): Boolean = number >= tuple._1 && number <= tuple._2

    def <(tuple: (Int, Int)): Boolean = number < tuple._1

    def >(tuple: (Int, Int)): Boolean = number > tuple._2

    def toInfluenceValue: Double = number.toDouble
  }

}
