package scala.model.environment.property.source

import scala.model.environment.property.Property

/**
 * A property source that influence the whole environment
 *
 * @tparam T type of property
 *
 * @author Paolo Baldini
 */
trait GlobalSource[T <: Property] extends PropertySource[T]