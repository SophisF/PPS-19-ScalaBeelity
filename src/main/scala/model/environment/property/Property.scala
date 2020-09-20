package scala.model.environment.property

import breeze.linalg.DenseMatrix

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
  type StateType <: State
  type VariationType <: Variation

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

    def numericRepresentation(percentage: Boolean = true): Int
  }

  trait Variation {

    def isNull: Boolean

    def vary[S <: StateType](state: S): StateType
  }

  def generateFilter(xDecrement: Int, yDecrement: Int): DenseMatrix[VariationType]
}