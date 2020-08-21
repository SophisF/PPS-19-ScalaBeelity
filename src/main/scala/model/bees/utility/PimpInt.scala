package scala.model.bees.utility

object PimpInt {

  implicit class MyInt(number: Int) {
    def in(tuple: (Int, Int)): Boolean = number >= tuple._1 && number <= tuple._2

    def <(tuple: (Int, Int)): Boolean = number < tuple._1

    def >(tuple: (Int, Int)): Boolean = number > tuple._2

    def toInfluenceValue: Double = number.toDouble
  }

}
