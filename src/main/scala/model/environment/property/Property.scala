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
    def value: ValueType

    /**
     * Determine the variation based on the passed value
     *
     * @param value of the variation
     * @return the varied state
     */
    def varyBy(value: ValueType): State
  }
}