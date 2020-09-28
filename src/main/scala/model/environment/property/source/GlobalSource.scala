package scala.model.environment.property.source

import scala.model.Time
import scala.model.Time.now
import scala.model.environment.property.{Property, TimeDependentProperty}

/**
 * A property source that influences the whole environment
 *
 * @tparam T type of property
 */
trait GlobalSource[T <: Property] extends PropertySource[T] {
  def variation: T#Variation
}

object GlobalSource {

  /**
   * An implementation for the global-source who takes time-dependent properties
   *
   * @param globalVariation the time-dependent variation of the property
   * @tparam T type of property
   */
  case class SeasonalSource[T <: TimeDependentProperty](
    globalVariation: T#TimedVariation
  ) extends GlobalSource[T] {

    def variation: T#Variation = variation(now())

    def variation(instant: Time): T#Variation = globalVariation instantaneous instant
  }
}