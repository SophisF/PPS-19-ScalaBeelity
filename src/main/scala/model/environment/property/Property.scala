package scala.model.environment.property

import breeze.linalg.DenseMatrix

/**
 * Represent an environmental property.
 * It can be both a quantitative one (e.g., temperature, pressure),
 * or a qualitative one (e.g., a cell could be an aquatic or terrestrial one).
 */
private[model] trait Property {
  /** Type of values of the property */
  type ValueType
  type StateType <: State
  type VariationType <: Variation

  /** Represents the state of a property */
  trait State {

    /**
     * State value
     *
     * @return the value of the state
     */
    def value: ValueType

    /**
     * Returns a numeric representation of the property state.
     * It is used to distinct between values (e.g., in visualization et similia operations).
     * E.g.:
     *    for an enumeration of [VAL_1, VAL_2, VAL_3] I can possibly return 1, 2, 3
     *
     * @param percentage if true, it requires that the value is in a percentage representation.
     *                   Otherwise, no output range (0..100) is required
     * @return the numeric representation of the value
     */
    def numericRepresentation(percentage: Boolean = true): Int
  }

  /** Represents the variation of a property who will be applied to a property state */
  trait Variation {

    /**
     * Specify if the variation could possibly generate a change into a state.
     * E.g.:
     *    variation of an integer property who will sum a 0 value would not generate any change in the state.
     *
     * @return true if the variation could possibly change the state, false otherwise
     */
    def isNull: Boolean

    /**
     * Vary a state given the specified strategy embedded into the variation object
     *
     * @param state to vary
     * @tparam S type of the state
     * @return the varied version of the state
     */
    def vary[S <: StateType](state: S): StateType
  }

  /**
   * Generate a filter of variations
   *
   * @param xDecrement influence the width of the filter
   * @param yDecrement influence the height of the filter
   * @return the filter of variations
   */
  def generateFilter(xDecrement: Int, yDecrement: Int): DenseMatrix[VariationType]
}