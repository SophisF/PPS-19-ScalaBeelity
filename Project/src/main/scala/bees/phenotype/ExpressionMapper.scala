package bees.phenotype

object ExpressionMapper {

  implicit def mapRangeExpression(min: Int)(max: Int)(influenceValue: Double)(rangeTuning: Int): (Int, Int) = {
    val step: Double = (max - min).toDouble / 100
    val xMin: Double  = min + step * influenceValue - rangeTuning
    val xMax: Double = min + step * influenceValue + rangeTuning
    (math.round(xMin).toInt, math.round(xMax).toInt)

  }


  implicit def mapIntExpression(min: Int)(max: Int)(influenceValue: Double): Int = {
    val step: Double = (max - min).toDouble / 100
    val x: Double = min + step*influenceValue
    math.round(x).toInt
  }

}
