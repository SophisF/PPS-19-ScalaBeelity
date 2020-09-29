package scala.model.environment.property.realization

import Math.{sin, toRadians}

import scala.model.Time
import scala.utility.MathHelper.intValueOf

/**
 * A TemperatureProperty is a property who works with data of type Int and has a behaviour based on the time.
 * This file contains configurations data for the specified property.
 */
private[environment] sealed trait TemperatureProperty extends IntProperty with IntTimedProperty

private[environment] object TemperatureProperty extends TemperatureProperty {
  private val seasonalVariationMultiplier = .25

  override val default: Int = 20
  override val maxValue: Int = 40
  override val minValue: Int = -10

  override def seasonalTrend: TimedVariationType =  new TimedVariationType {
    private var lastGet: Int = 0

    override def instantaneous(instant: Time): VariationType = {
      val monthlyValue = rangeCenter * seasonalVariationMultiplier * -sin(toRadians(instant % 365))
      val variation: Int = monthlyValue - lastGet
      lastGet = monthlyValue
      variation
    }
  }
}