package scala.model.environment.property.realization

import scala.model.environment.property.realization.integer.TimedProperty
import scala.model.environment.property.realization.integer.utils.TimedFilterGenerator

/**
 * A PressureProperty is a property who works with data of type Int and has a behaviour based on the time.
 * However, it has no specific/custom seasonal trend.
 * This file contains configurations data for the specified property.
 */
private[environment] sealed trait PressureProperty extends TimedProperty with TimedFilterGenerator

private[environment] object PressureProperty extends PressureProperty {
  override val default: Int = 1000
  override val maxValue: Int = 1080
  override val minValue: Int = 980
}