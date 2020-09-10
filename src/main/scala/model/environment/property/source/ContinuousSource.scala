package scala.model.environment.property.source

import breeze.linalg.DenseMatrix

import scala.model.environment.property.Property
import scala.model.environment.property.source.InstantaneousSource.InstantaneousSourceImpl
import scala.model.environment.time.{FiniteData, Time}

sealed trait ContinuousSource[T <: Property] extends ZoneSource[T] with FiniteData[DenseMatrix[T#Variation]] {
  def percentage: (T#ValueType, Int) => T#ValueType
}

object ContinuousSource {

  def apply[T <: Property](
    _filter: DenseMatrix[T#Variation],
    _x: Int, _y: Int,
    _width: Int, _height: Int,
    _fireTime: Time = Time.now(), _daysDuration: Int
  )(
    implicit _percentage: (T#ValueType, Int) => T#ValueType
  ): ContinuousSource[T] = new ContinuousSource[T] {

    override def filter: DenseMatrix[T#Variation] = _filter
    override def percentage: (T#ValueType, Int) => T#ValueType = _percentage

    override var evaluated: Int = 0

    override val width: Int = _width
    override val height: Int = _height
    override val x: Int = _x
    override val y: Int = _y

    override def fireTime: Time = _fireTime
    override def daysDuration: Int = _daysDuration
  }

  implicit def nextValue[T <: Property](source: ContinuousSource[T]): DenseMatrix[T#Variation] = {
    DenseMatrix.create(source.filter.rows, source.filter.cols, source.filter.data.map(variation =>
      GenericVariation(source.percentage(variation.value, force))))
  }

  implicit def instantaneous[T <: Property](source: ContinuousSource[T]): Option[InstantaneousSource[T]] =
    FiniteData.dataAtInstant[DenseMatrix[T#Variation], ContinuousSource[T]](source)(nextValue[T])
      .map(filter => InstantaneousSourceImpl(filter, source.x, source.y, source.width, source.height))

  //def linearize(filter: ContinuousPropertySource[T], instant: Int): Option[Array[PointVariation[T]]] =
  //  lastFired(filter, instant) match {
  //    case None => Option.empty
  //    case Some(value) => Option.apply(
  //      linearize(InstantaneousPropertySource(value, filter.x, filter.y, filter.width, filter.height))
  //    )
  //  }
}
