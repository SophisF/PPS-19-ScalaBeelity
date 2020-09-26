package scala.model.environment.property.realization

import Math.{cos, toRadians}

import scala.model.Time
import scala.utility.MathHelper.intValueOf

/**
 * A TemperatureProperty is a property who works with data of type Int and has a behaviour based on the time.
 * This file contains configurations data for the specified property.
 */
sealed trait TemperatureProperty extends IntProperty with IntTimedProperty

object TemperatureProperty extends TemperatureProperty {
  private val seasonalVariationMultiplier = .25

  override val default: Int = 20
  override val maxValue: Int = 40
  override val minValue: Int = -10

  override def seasonalTrend: TimedVariationType =  new TimedVariationType {
    private var lastGet: Int = 0

    override def instantaneous(instant: Time): VariationType = {
      // TODO che ne dici gnagna? Così ha un andamento più smooth e complementare a quello dell'umidità -> molto caldo => secco; molto freddo => umido
      val monthlyValue = rangeCenter * seasonalVariationMultiplier * cos(toRadians(instant % 365))
      //val monthlyValue = (0 to 6).mirror(false).map(_ * MonthlyIncrement).drop(instant month).head
      val variation: Int = monthlyValue - lastGet
      lastGet = monthlyValue
      variation
    }
  }
}