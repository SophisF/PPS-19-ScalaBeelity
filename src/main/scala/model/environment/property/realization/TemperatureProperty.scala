package scala.model.environment.property.realization

import Math.{cos, toRadians}

import scala.model.Time
import scala.model.environment.property.typed.int.{IntBehaviouralProperty, IntProperty}
import scala.utility.MathHelper.intValueOf

/**
 * A TemperatureProperty is a property who works with data of type Int and has a behaviour based on the time.
 * This file contains configurations data for the specified property.
 */
sealed trait TemperatureProperty extends IntProperty with IntBehaviouralProperty

object TemperatureProperty extends TemperatureProperty {
  private val variationMultiplier = .25

  override val default: Int = 20
  override val maxValue: Int = 40
  override val minValue: Int = -10

  override def monthlyValue(instant: Time): Int = rangeCenter * variationMultiplier * cos(toRadians(instant % 365))
}