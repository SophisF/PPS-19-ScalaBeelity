package utility

object ExpressionMapper {

  //TODO check implicit
  implicit def getRange(min: Int, max: Int, averageValue: Double, influenceValueNormalized: Double, rangeTuning: Int): (Double, Double) = {
    val averageRange = (max - min).toDouble/2

    val x = influenceValueNormalized*averageRange/averageValue
    val xMin = if (x-rangeTuning*x/100 < min) min else x-rangeTuning*x/100
    val xMax = if (x+rangeTuning*x/100 > max) max else x+rangeTuning*x/100
    (xMin, xMax)
  }

  implicit def getRange(max: Int, averageValue: Double, influenceValueNormalized: Double ): Int ={
     if (influenceValueNormalized*max/(2*averageValue) > max ) max else (influenceValueNormalized*max/(2*averageValue)).toInt
  }

}
