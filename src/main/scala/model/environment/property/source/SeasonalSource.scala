package scala.model.environment.property.source

import scala.model.environment.property.TimedProperty
import scala.model.environment.time.Time

class SeasonalSource[T <: TimedProperty](
  val globalVariation: T#TimedVariation
) extends GlobalSource[T] {

  def nextValue(instant: Time = Time.now()): T#Variation = globalVariation.instantaneous(instant)
  /*implicit def nextValue[T <: Property](data: SeasonalSource[T]): T#ValueType = {
    val lastTime = data.lastGet
    data.lastGet = Time.now()
    data.nextValue(lastTime, data.lastGet)
  }*/
}