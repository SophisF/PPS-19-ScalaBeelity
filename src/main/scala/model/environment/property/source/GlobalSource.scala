package scala.model.environment.property.source

import scala.model.Time
import scala.model.environment.property.{Property, TimeDependentProperty}

/**
 * A property source that influence the whole environment
 *
 * @tparam T type of property
 *
 * @author Paolo Baldini
 */
trait GlobalSource[T <: Property] extends PropertySource[T] {
  def variation: T#Variation
}

object GlobalSource {

  case class SeasonalSource[T <: TimeDependentProperty](
    globalVariation: T#TimedVariation
  ) extends GlobalSource[T] {

    override def variation: T#Variation = variationAt()

    def variationAt(instant: Time = Time.now()): T#Variation = globalVariation instantaneous instant
  }
}