package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

object PressurePropertyHelper extends IntegerPropertyHelper[PressureProperty] {

  override def generateInstantaneousFilter(width: Int, height: Int): DenseMatrix[PressureProperty#Variation] = ???
}