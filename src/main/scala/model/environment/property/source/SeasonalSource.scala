package scala.model.environment.property.source

import scala.model.environment.property.Property
import scala.model.environment.time.Time

trait SeasonalSource[T <: Property] extends GlobalSource[T] {
  var lastGet: Time = Time.now()
  def nextValue: (Time, Time) => T#ValueType
}

object SeasonalSource {

  case class SeasonalSourceImpl[T <: Property](nextValue: (Time, Time) => T#ValueType) extends SeasonalSource[T]

  def apply[T <: Property](nextValue: (Time, Time) => T#ValueType): SeasonalSource[T] =
    SeasonalSourceImpl[T](nextValue)

  implicit def nextValue[T <: Property](data: SeasonalSource[T]): T#ValueType = {
    val lastTime = data.lastGet
    data.lastGet = Time.now()
    data.nextValue(lastTime, data.lastGet)
  }
}