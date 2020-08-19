package scala.model.property.source

import scala.model.property.Property
import scala.model.time.Time

trait SeasonalPropertySource[T <: Property] extends PropertySource[T] {
  var lastGet: Double = 0 // TODO eventualmente fare privato e rendere nextValue metodo
  def nextValue: (Int, Int) => T#ValueType
}

object SeasonalPropertySource {

  def apply[T <: Property](implicit operation: (Int, Int) => T#ValueType): SeasonalPropertySource[T] =
    new SeasonalPropertySource[T] {
      override val nextValue: (Int, Int) => T#ValueType = operation
    }

  implicit def nextValue[T <: Property](data: SeasonalPropertySource[T]): T#ValueType = {
    val lastTime = data.lastGet
    data.lastGet = Time.time
    data.nextValue(lastTime toInt, data.lastGet toInt)
  }
}