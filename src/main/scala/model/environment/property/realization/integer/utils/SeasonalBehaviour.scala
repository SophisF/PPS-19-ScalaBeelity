package scala.model.environment.property.realization.integer.utils

import scala.model.Time
import scala.model.environment.property.realization.integer.TimedProperty
import scala.model.environment.property.utils.{SeasonalBehaviour => Utils}
import scala.utility.SugarBowl.RichMappable

trait SeasonalBehaviour extends Utils { this: TimedProperty =>
  def monthlyValue(instant: Time): Int

  def seasonalTrend: TimedVariationType = new TimedVariationType {
    private var lastGet: Int = 0

    override def instantaneous(instant: Time): VariationType = (monthlyValue(instant) - lastGet) ~> (variationValue => {
      lastGet += variationValue
      variationValue
    })
  }
}
