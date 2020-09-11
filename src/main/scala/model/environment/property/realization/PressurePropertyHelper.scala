package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.environment.property.FilterBuilder
import scala.util.Random

object PressurePropertyHelper extends IntegerPropertyHelper[PressureProperty] {

  override def generateInstantaneousFilter(width: Int, height: Int): DenseMatrix[PressureProperty#Variation] = FilterBuilder
    .gaussianFunction3d(Random.between(PressureProperty.minValue +1, PressureProperty.maxValue +1), 1, width, height)
    .mapValues(it => PressureProperty.variation(it).asInstanceOf[PressureProperty#Variation])
}