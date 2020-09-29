package scala.model.environment.property.realization.integer

import scala.model.environment.property.{Property => Property_}
import scala.utility.SugarBowl.RichMappable

/**
 * Represent a generic property who works with data of type Int.
 * Simplify the use (and help DRY) of numeric properties
 */
trait Property extends Property_ with Range {
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

    override def numericRepresentation(percentage: Boolean = true): Int =
      value.when (_ => percentage) ~> (v => (v - minValue) * 100 / rangeWidth)

    override def equals(a: Any): Boolean = a match { case state: IntegerState => state.value == value; case _ => false }
  }

  implicit def variation(_value: Int): VariationType = new IntegerVariation {
    override def value: Int = _value
  }

  /** A partial implementation of a variation for an integer-type property */
  trait IntegerVariation extends Variation {

    def value: Int

    def isNull: Boolean = value == 0

    override def vary[S <: State](_state: S): StateType = _state.value + value
  }
}