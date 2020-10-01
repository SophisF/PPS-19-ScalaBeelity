package scala.model.environment.property.source

import scala.model.environment.property.Property

/**
 * Represent a source of property variations
 *
 * @tparam T type of property
 */
private[environment] trait PropertySource[T <: Property]