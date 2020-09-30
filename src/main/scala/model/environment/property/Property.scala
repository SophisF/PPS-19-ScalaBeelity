package scala.model.environment.property

/**
 * This represent an environmental property defined as an entity
 * having a variable state and a variation strategy embedded in it.
 * It can be both a quantitative one (e.g., temperature, pressure),
 * or a qualitative one (e.g., a land can be wooded, arid, etc.)
 */
private[model] trait Property {

  /** Type of values of the property */
  type ValueType

  /**
   * Allow to specify a custom property-state for each implementation.
   * For properties which have more than one state implementation,
   * the creation of a common base type (like a simple trait) is suggested.
   *
   * Usage example:
   *
   *    StateA
   *     /  \        variation(B) -> StateA; variation(C) -> StateA
   *    B   C
   *
   *   Specific type checking could be done inside the variation strategy, if needed
   */
  type StateType <: State

  /**
   * Specify the variation strategy of a property.
   * For usage usage tips, please refer to state-type docs
   * considering its usage in generate-filter context
   */
  type VariationType <: Variation

  /**
   * Represents the 'state' of a property.
   * It can be seen as the manifestation of the property
   * who assumes a specific value
   */
  trait State {

    /**
     * Value of the property in this specific state
     *
     * @return the value of the state
     */
    def value: ValueType

    /**
     * Returns a numeric representation of the property state.
     * It is used to distinct between values (e.g., in visualization et similia operations).
     *
     * E.g., for an enumeration of [VAL_1, VAL_2, VAL_3] I can possibly return 1, 2, 3
     *
     * @param percentage if true, it requires that the value is in a percentage representation.
     *                   Otherwise, no output range (0..100) is required
     * @return the numeric representation of the value
     */
    def numericRepresentation(percentage: Boolean = true): Int
  }

  /** Represents the variation of a property who will affect a property state */
  trait Variation {

    /**
     * Specify if the variation could possibly generate a change into a state.
     *
     * E.g., variation of an integer property who will sum a 0 to the value will not generate any change in the state.
     *
     * @return true if the variation could possibly change the state, false otherwise
     */
    def isNull: Boolean

    /**
     * Vary a state given the specified strategy embedded into the variation object
     *
     * @param state to vary
     * @tparam S type of the state. It could eventually be a subtype of the one specified as StateType
     * @return the varied version of the state
     */
    def vary[S <: StateType](state: S): StateType
  }
}
