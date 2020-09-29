package scala.model.environment.property.utils

import scala.model.environment.property.TimedProperty

/**
 * Represents a property with a seasonal behaviour.
 * It allow to identify properties who has a variation
 * during the course of the year
 */
trait SeasonalBehaviour { this: TimedProperty =>

  /**
   * Return a variation who represent the deviation from a default value in a specific period of the year
   *
   * @return the variation from default value in this instant
   */
  def seasonalTrend: TimedVariationType
}
