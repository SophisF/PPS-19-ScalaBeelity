package scala.model.environment.property.typed.int

import scala.model.Time
import scala.model.environment.property.BehaviouralProperty
import scala.utility.SugarBowl.RichMappable

trait IntBehaviouralProperty extends BehaviouralProperty with IntTimedProperty {
  def monthlyValue(instant: Time): Int

  def seasonalTrend: TimedVariationType = new TimedVariationType {
    private var lastGet: Int = 0

    override def instantaneous(instant: Time): VariationType = (monthlyValue(instant) - lastGet) ~> (variationValue => {
      lastGet += variationValue
      variationValue
    })
  }
}
