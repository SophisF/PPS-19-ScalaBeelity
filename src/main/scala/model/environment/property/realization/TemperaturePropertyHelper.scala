package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.environment.property.FilterBuilder
import scala.model.environment.property.realization.TemperatureProperty._
import scala.util.Random

object TemperaturePropertyHelper extends IntegerPropertyHelper[TemperatureProperty] {

  //implicit def percentage(value: Int, percent: Int): Int = value * percent / 100

  override def generateInstantaneousFilter(width: Int, height: Int): DenseMatrix[TemperatureProperty#Variation] =
    FilterBuilder.gaussianFunction3d(Random.between(minValue +1, maxValue +1),
      1, width, height).mapValues(it => variation(it).asInstanceOf[TemperatureProperty#Variation])

  override def percentage(state: TemperatureProperty#State): Double = (state.value - minValue) * 100 / (maxValue - minValue)
}
