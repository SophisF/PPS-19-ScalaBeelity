package scala.model.environment.property.source

import breeze.linalg.DenseMatrix

import scala.model.environment.Environment.applyFilter
import scala.model.environment.property.Property
import scala.model.environment.property.Variation.GenericVariation
import scala.model.environment.property.source.InstantaneousSource.InstantaneousSourceImpl
import scala.model.environment.time.{FiniteData, Time}

sealed trait ContinuousSource[T <: Property] extends ZoneSource[T] with FiniteData[DenseMatrix[GenericVariation[T]]] {
  def percentage: (T#ValueType, Int) => T#ValueType
}

object ContinuousSource {

  case class ContinuousSourceImpl[T <: Property](
    filter: DenseMatrix[GenericVariation[T]],
    percentage: (T#ValueType, Int) => T#ValueType,
    x: Int, y: Int,
    width: Int, height: Int,
    fireTime: Time = Time.now(), daysDuration: Int
  ) extends ContinuousSource[T] {
    var evaluated: Int = 0
  }

  implicit def nextValue[T <: Property](source: ContinuousSource[T]): DenseMatrix[GenericVariation[T]] = {
    val force = (Time.now() - source.fireTime) * 100 / source.daysDuration - source.evaluated
    source.evaluated += force
    DenseMatrix.create(source.filter.rows, source.filter.cols, source.filter.data.map(variation =>
      GenericVariation(source.percentage(variation.value, force))))
  }

  implicit def instantaneous[T <: Property](source: ContinuousSource[T]): Option[InstantaneousSource[T]] =
    FiniteData.dataAtInstant[DenseMatrix[GenericVariation[T]], ContinuousSource[T]](source)(nextValue[T])
      .map(filter => InstantaneousSourceImpl(filter, source.x, source.y, source.width, source.height))

  //def linearize(filter: ContinuousPropertySource[T], instant: Int): Option[Array[PointVariation[T]]] =
  //  lastFired(filter, instant) match {
  //    case None => Option.empty
  //    case Some(value) => Option.apply(
  //      linearize(InstantaneousPropertySource(value, filter.x, filter.y, filter.width, filter.height))
  //    )
  //  }
}
