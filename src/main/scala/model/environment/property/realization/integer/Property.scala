package scala.model.environment.property.realization.integer

import scala.model.environment.property.{Property => Property_}
import scala.utility.SugarBowl.RichMappable

/**
 * Represent a generic property who works with data of type Int.
 * Simplify the use (and help DRY) of numeric properties
 */
private[realization] trait Property extends Property_ with Range {
  private val percent: Int = 100

  override type StateType = IntegerState
  override type VariationType = IntegerVariation

  /**
   * Represent a default value for the property state
   *
   * @return the default value of the state
   */
  def default: Int

  /**
   * Build a state from an Int
   *
   * @param _value to set as the state value
   * @return the built state
   */
  implicit def state(_value: Int): IntegerState = new IntegerState {
    override val value: Int = limit(_value)
  }

  /** An implementation of a state for an integer-type property */
  trait IntegerState extends State {

    /**
     * Returns the value of the state or its percentage if the 'percentage' parameter is true
     *
     * @param percentage if true, it requires that the value is in a percentage representation.
     *                   Otherwise, no output range (0..100) is required
     *  @return the numeric representation of the value
     */
    override def numericRepresentation(percentage: Boolean = true): Int =
      value.when(_ => percentage) ~> (v => (v - minValue) * percent / rangeWidth)

    override def equals(a: Any): Boolean = a match {
      case state: IntegerState => state.value == value;
      case _ => false
    }
  }

  /**
   * Build a variation from an Int
   *
   * @param _value to set as the variation value
   * @return the built variation
   */
  implicit def variation(_value: Int): VariationType = new IntegerVariation {
    override def value: Int = _value
  }

  /** A partial implementation of a variation for an integer-type property */
  trait IntegerVariation extends Variation {

    /**
     * Value to be added in the strategy
     *
     * @return the value to be added
     */
    def value: Int

    /**
     * If the variation's value is 0 returns true, returns false otherwise
     *
     * @return true if the variation could possibly change the state, false otherwise
     */
    def isNull: Boolean = value == 0

    /**
     * Vary a state by applying a sum function
     *
     * @param _state to be varied
     * @tparam S type of the state. It could eventually be a subtype of the one specified as StateType
     * @return the varied version of the state
     */
    override def vary[S <: State](_state: S): StateType = _state.value + value
  }
}