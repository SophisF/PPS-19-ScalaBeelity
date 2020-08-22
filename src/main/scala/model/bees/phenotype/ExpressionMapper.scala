package scala.model.bees.phenotype

import scala.model.bees.genotype.Gene

/**
 * Singleton that implements strategies to map a value into an expression.
 */
object ExpressionMapper {

  /**
   * Implicit method to map the influence value into a range.
   * @param min the min value of the range to map the influence value.
   * @param max the max value of the range to map the influence value.
   * @param influenceValue the value to map.
   * @param rangeTuning the value to add or subtract to get a range value.
   * @return a tuple that represents the range of value.
   */
  implicit def mapRangeExpression(min: Int)(max: Int)(influenceValue: Double)(rangeTuning: Int): (Int, Int) = {
    val step: Double = (max - min).toDouble / Gene.maxFrequency
    val xMin: Double  = min + step * influenceValue - rangeTuning
    val xMax: Double = min + step * influenceValue + rangeTuning
    (math.round(xMin).toInt, math.round(xMax).toInt)

  }

  /**
   * Implicit method to map the influence value into a int value.
   * @param min the min value to map the influence value.
   * @param max the max value to map the influence value.
   * @param influenceValue the value to map.
   * @return a int that represents the mapped value.
   */
  implicit def mapIntExpression(min: Int)(max: Int)(influenceValue: Double): Int = {
    val step: Double = (max - min).toDouble / Gene.maxFrequency
    val x: Double = min + step*influenceValue
    math.round(x).toInt
  }

}
