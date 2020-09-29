package scala.model.environment.property.realization

import Math.{sin, toRadians}

import scala.model.Time
import scala.model.environment.property.realization.integer.TimedProperty
import scala.model.environment.property.realization.integer.utils.{SeasonalBehaviour, TimedFilterGenerator}
import scala.utility.MathHelper.intValueOf

/**
 * A TemperatureProperty is a property who works with data of type Int and has a behaviour based on the time.
 * This file contains configurations data for the specified property.
 */
private[environment]
sealed trait TemperatureProperty extends TimedProperty with SeasonalBehaviour with TimedFilterGenerator

private[environment] object TemperatureProperty extends TemperatureProperty {
  private val variationMultiplier = .25

  override val default: Int = 20
  override val maxValue: Int = 40
  override val minValue: Int = -10

  override def monthlyValue(instant: Time): Int = rangeCenter * variationMultiplier * -sin(toRadians(instant % 365))
}