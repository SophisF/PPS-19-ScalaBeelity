package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.environment.property.Variation

object TemperaturePropertyHelper extends IntegerPropertyHelper[TemperatureProperty] {

  //implicit def percentage(value: Int, percent: Int): Int = value * percent / 100

  override def generateFilter(width: Int, height: Int): DenseMatrix[Variation.GenericVariation[TemperatureProperty]] = ???
}
