package scala.model.environment.property

/**
 * Represent an environmental property.
 * It can be both a quantitative one (e.g., temperature, pressure),
 * or a qualitative one (e.g., a cell could be an aquatic or terrestrial one).
 *
 * @author Paolo Baldini
 */
trait Property {
  type ValueType

  def default: ValueType
}