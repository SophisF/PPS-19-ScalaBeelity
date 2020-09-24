package scala.model.environment.property.realization

import Math.{sin, toRadians}

import scala.model.Time
import scala.utility.MathHelper.intValueOf

sealed trait HumidityProperty extends IntProperty with IntTimedProperty

object HumidityProperty extends HumidityProperty {
  override val default: Int = 30
  override val maxValue: Int = 100
  override val minValue: Int = 0

  override def seasonalTrend: TimedVariationType = new TimedVariationType {
    private var lastGet: Int = 0

    override def instantaneous(instant: Time): VariationType = {
      val monthlyValue = rangeCenter * .25 * sin(toRadians(instant % 365))
      val variation: Int = monthlyValue - lastGet
      lastGet = monthlyValue
      variation
    }
  }
}