package scala.model.property

import scala.model.property.Property.Property
import scala.model.time.{Time, TimeData}

trait PropertySource

object PropertySource {

  // TODO case class con var?
  case class SeasonalPropertySource(property: Property) extends PropertySource with TimeData[Int] {
    var lastGet: Double = 0
  }

  implicit def nextValueSin(data: SeasonalPropertySource): Int = {
    val sin = Math sin (Time.time % 365)
    val variance = sin - data.lastGet
    data.lastGet = sin
    variance toInt
  }
  
  def incrementalValue(time : Int) : Int =(0 until 12 map (x => if (x < 6) x * 3 else (12 - x) * 3))
    .drop((time / 30) % 12).head

  // TODO ??? non sono sicuro sia giusto il calcolo, vedere se si può accedere con indice
  implicit def nextValueLinear(data: SeasonalPropertySource): Int = {
    val result = incrementalValue(Time.time) - incrementalValue(data.lastGet toInt)
    data.lastGet = Time.time
    result
  }

}
