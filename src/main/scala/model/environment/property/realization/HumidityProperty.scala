package scala.model.environment.property.realization

import Math.{sin, toRadians}

import scala.model.Time
import scala.model.environment.property.realization.integer.utils.{SeasonalBehaviour, TimedFilterGenerator}
import scala.model.environment.property.realization.integer.TimedProperty
import scala.utility.MathHelper.intValueOf

/**
 * A HumidityProperty is a property who works with data of type Int and has a behaviour based on the time.
 * This file contains configurations data for the specified property.
 */
sealed trait HumidityProperty extends TimedProperty with SeasonalBehaviour with TimedFilterGenerator

object HumidityProperty extends HumidityProperty {
  private val variationMultiplier = .25

  override val default: Int = 30
  override val maxValue: Int = 100
  override val minValue: Int = 0

  override def monthlyValue(instant: Time): Int = rangeCenter * variationMultiplier * sin(toRadians(instant % 365))
}