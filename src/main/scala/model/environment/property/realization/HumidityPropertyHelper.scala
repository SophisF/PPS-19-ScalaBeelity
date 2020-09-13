package scala.model.environment.property.realization

import scala.util.Random
import breeze.linalg.DenseMatrix

import scala.model.environment.property.FilterBuilder
import scala.model.environment.property.realization.HumidityProperty._

object HumidityPropertyHelper extends IntegerPropertyHelper[HumidityProperty] {

  /**
   * Generate a gaussian filter for the HumidityProperty
   *
   * @param width how much the value should descent rapidly in x axis
   * @param height how much the value should descent rapidly in y axis
   * @return the gaussian filter
   */
  override def generateInstantaneousFilter(width: Int, height: Int): DenseMatrix[HumidityProperty#Variation] = FilterBuilder
    .gaussianFunction3d(Random.between(minValue +1, maxValue +1), 1, width, height)
    .mapValues(it => variation(it).asInstanceOf[HumidityProperty#Variation])

  override def percentage(state: HumidityProperty#State): Double = (state.value - minValue) * 100 / (maxValue - minValue)
}