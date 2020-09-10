package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.environment.time.FiniteData

object TemperaturePropertyHelper extends IntegerPropertyHelper[TemperatureProperty] {

  //implicit def percentage(value: Int, percent: Int): Int = value * percent / 100

  override def generateInstantaneousFilter(width: Int, height: Int): DenseMatrix[TemperatureProperty#Variation] = ???

  override def generateContinuousFilter(width: Int, height: Int, duration: Int): DenseMatrix[Int => Option[TemperatureProperty#Variation]] = ???

  override def generateFiniteFilter(width: Int, height: Int, duration: Int): DenseMatrix[FiniteData[TemperatureProperty#Variation]] = ???
}
