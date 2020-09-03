package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.environment.property.Variation

object PressurePropertyHelper extends IntegerPropertyHelper[PressureProperty] {

  override def generateFilter(width: Int, height: Int): DenseMatrix[Variation.GenericVariation[PressureProperty]] = ???
}