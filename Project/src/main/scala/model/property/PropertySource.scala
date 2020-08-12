package scala.model.property

import scala.model.property.Property.Property
import scala.model.time.PeriodicalData

trait PropertySource

object PropertySource {

  case class SeasonalPropertySource (
    property: Property,
    var lastInference: Int,
    inference: (PeriodicalData[Int], Int) => Int
  ) extends PropertySource with PeriodicalData[Int]
}