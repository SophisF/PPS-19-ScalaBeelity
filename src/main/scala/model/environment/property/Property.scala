package scala.model.environment.property

/**
 * Represent an environmental property.
 * It can be both a quantitative one (e.g., temperature, pressure),
 * or a qualitative one (e.g., a cell could be an aquatic or terrestrial one).
 *
 * @author Paolo Baldini
 */
trait Property {
  /**
   * Type of values of the property
   */
  type ValueType

  /**
   * Default property state
   *
   * @return the default property state
   */
  def default: ValueType

  /**
   * Represent the state of a property
   */
  trait State {
    /**
     * State value
     *
     * @return the value of the state
     */
    def value: ValueType = default
  }

  trait Variation {
    def vary[S <: State](state: S): State
  }
}