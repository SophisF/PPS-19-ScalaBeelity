package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.environment.property.FilterBuilder
import scala.model.environment.property.realization.PressureProperty._
import scala.util.Random

object PressurePropertyHelper extends IntegerPropertyHelper[PressureProperty] {

  override def generateInstantaneousFilter(width: Int, height: Int): DenseMatrix[PressureProperty#Variation] =
    FilterBuilder.gaussianFunction3d(maxValue, // todo change maxvalue to random
      0, width, height)
    .mapValues(it => variation(it).asInstanceOf[PressureProperty#Variation])

  override def percentage(state: PressureProperty#State): Double = (state.value - minValue) * 100 / (maxValue - minValue)
}