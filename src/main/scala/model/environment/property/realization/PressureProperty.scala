package scala.model.environment.property.realization

import scala.model.environment.property.typed.int.{IntProperty, IntTimedProperty}

/**
 * A PressureProperty is a property who works with data of type Int and has a behaviour based on the time.
 * However, it has no specific/custom seasonal trend.
 * This file contains configurations data for the specified property.
 */
sealed trait PressureProperty extends IntProperty with IntTimedProperty

object PressureProperty extends PressureProperty {
  override val default: Int = 1000
  override val maxValue: Int = 1080
  override val minValue: Int = 980
}