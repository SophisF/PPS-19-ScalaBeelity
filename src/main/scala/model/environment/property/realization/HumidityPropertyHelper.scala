package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.environment.property.Variation.GenericVariation
import scala.model.environment.property.FilterBuilder
import scala.util.Random

object HumidityPropertyHelper extends IntegerPropertyHelper[HumidityProperty] {

  /**
   * Generate a gaussian filter for the HumidityProperty
   *
   * @param width how much the value should descent rapidly in x axis
   * @param height how much the value should descent rapidly in y axis
   * @return the gaussian filter
   */
  override def generateFilter(width: Int, height: Int): DenseMatrix[GenericVariation[HumidityProperty]] = FilterBuilder
    .gaussianFunction3d(Random.between(HumidityProperty.minValue +1, HumidityProperty.maxValue +1), 1, width, height)
    .mapValues(it => GenericVariation[HumidityProperty](it))
}