package scala.model.environment.property.realization

import scala.util.Random
import breeze.linalg.DenseMatrix

import scala.model.environment.property.FilterBuilder
import scala.model.environment.property.realization.HumidityProperty.HumidityVariation

object HumidityPropertyHelper extends IntegerPropertyHelper[HumidityProperty] {

  /**
   * Generate a gaussian filter for the HumidityProperty
   *
   * @param width how much the value should descent rapidly in x axis
   * @param height how much the value should descent rapidly in y axis
   * @return the gaussian filter
   */
  override def generateInstantaneousFilter(width: Int, height: Int): DenseMatrix[HumidityProperty#Variation] = FilterBuilder
    .gaussianFunction3d(Random.between(HumidityProperty.minValue +1, HumidityProperty.maxValue +1), 1, width, height)
    .mapValues(it => HumidityProperty.variation(it)).asInstanceOf[DenseMatrix[HumidityProperty#Variation]]
}