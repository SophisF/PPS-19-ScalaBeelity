package scala.model.environment.property.realization

import Math.{sin, toRadians}

import scala.model.Time

sealed trait HumidityProperty extends IntegerProperty with IntegerTimedProperty

object HumidityProperty extends HumidityProperty {
  override val default: Int = 30
  override val maxValue: Int = 100
  override val minValue: Int = 0

  override def seasonalTrend: TimedVariationType = new TimedVariationType {
    private var lastGet: Int = 0

    override def instantaneous(instant: Time): VariationType = {
      val monthlyValue = ((maxValue - minValue) / 2 + minValue) * .25 * sin(toRadians(Time.toDays(instant) % 365)) toInt
      val variation = monthlyValue - lastGet
      lastGet = monthlyValue
      variation
    }
  }
}