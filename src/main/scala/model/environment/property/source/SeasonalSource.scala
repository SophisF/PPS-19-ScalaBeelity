package scala.model.environment.property.source

import scala.model.environment.property.Property
import scala.model.environment.time.Time

trait SeasonalSource[T <: Property] extends GlobalSource[T] {
  var lastGet: Time = Time.now()
  def nextValue: (Time, Time) => T#ValueType
}

object SeasonalSource {

  implicit def nextValue[T <: Property](data: SeasonalSource[T]): T#ValueType = {
    val lastTime = data.lastGet
    data.lastGet = Time.now()
    data.nextValue(lastTime, data.lastGet)
  }
}