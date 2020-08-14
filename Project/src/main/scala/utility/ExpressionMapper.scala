package utility

object ExpressionMapper {

  //TODO check implicit
  //TODO create 1 function with function passing
   def getRange(min: Int, max: Int, averageValue: Double, influenceValueNormalized: Double, rangeTuning: Int): (Int, Int) = {
    val averageRange = (max - min).toDouble/2

    val x = influenceValueNormalized*averageRange/averageValue
    val xMin = if (x-rangeTuning*x/100 < min) min else x-rangeTuning*x/100
    val xMax = if (x+rangeTuning*x/100 > max) max else x+rangeTuning*x/100
    (xMin.toInt, xMax.toInt)
  }

   def getRange(max: Int, averageValue: Double, influenceValueNormalized: Double ): Int ={
     if (influenceValueNormalized*max/(2*averageValue) > max ) max else (influenceValueNormalized*max/(2*averageValue)).toInt
  }

  def getRange(averageValue: Double, influenceValueNormalized: Double): Int = {
    if (influenceValueNormalized > averageValue) 2 else 1
  }

}
