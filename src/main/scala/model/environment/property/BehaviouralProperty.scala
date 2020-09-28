package scala.model.environment.property

/**
 * Represents a property with a seasonal behaviour.
 * It allow to identify properties who has a variation
 * during the course of the year
 */
trait BehaviouralProperty extends TimeDependentProperty {

  /**
   * Return a variation who represent the deviation from a default value in a specific period of the year
   *
   * @return the variation from default value in this instant
   */
  def seasonalTrend: TimedVariationType
}
