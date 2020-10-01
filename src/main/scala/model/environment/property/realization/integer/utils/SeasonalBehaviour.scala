package scala.model.environment.property.realization.integer.utils

import scala.model.Time
import scala.model.environment.property.realization.integer.TimedProperty
import scala.model.environment.property.utils.{SeasonalBehaviour => Utils}
import scala.utility.SugarBowl.RichMappable

/**
 * A seasonal-behaviour who works with timed-properties with value-type Int
 */
private[realization] trait SeasonalBehaviour extends Utils { this: TimedProperty =>

  /**
   * Returns the value of the trend in the specified instant
   *
   * @param instant of time on which calculate the value
   * @return the value in the specified instant
   */
  def monthlyValue(instant: Time): Int

  /**
   * Returns a timed-variation who embed the strategy to get the variation at a specified instant
   *
   * @return the variation from default value in this instant
   */
  def seasonalTrend: TimedVariationType = new TimedVariationType {
    private var lastGet: Int = 0

    /**
     * Returns the variation at the specified instant
     *
     * @param instant of which calculate the variation
     * @return the variation to use in this instant
     */
    override def instantaneous(instant: Time): VariationType = (monthlyValue(instant) - lastGet) ~> (variationValue => {
      lastGet += variationValue
      variationValue
    })
  }
}
