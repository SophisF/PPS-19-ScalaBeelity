package scala.model.environment.property.utils

import scala.model.environment.property.TimedProperty

/**
 * Adds a seasonal-behaviour to a timed property.
 * It allows to identify properties that have a variation
 * during the course of the year
 */
private[environment] trait SeasonalBehaviour { this: TimedProperty =>

  /**
   * Returns a variation who represents the deviation from a default value in a specific period of the year
   *
   * @return the variation from default value in this instant
   */
  def seasonalTrend: TimedVariationType
}
