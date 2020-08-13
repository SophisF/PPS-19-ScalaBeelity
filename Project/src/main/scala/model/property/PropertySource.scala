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

  // TODO ??? non sono sicuro sia giusto il calcolo
  implicit def nextValueLinear(data: SeasonalPropertySource): Int = {
    val applications = (Time.time - data.lastGet) % 30
    data.lastGet = Time.time

    applications * 2 toInt
  }
}