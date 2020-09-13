package scala.model.environment.property.source

import breeze.linalg.DenseMatrix

import scala.model.environment.property.Property
import scala.model.environment.time.{FiniteData, Time}

case class ContinuousSource[T <: Property](
  filter: DenseMatrix[T#Variation],
  x: Int, y: Int,
  width: Int, height: Int,
  fireTime: Time = Time.now(),
  daysDuration: Int
) extends ZoneSource[T] with FiniteData[DenseMatrix[T#Variation]] {
  override var evaluated: Int = 0
}