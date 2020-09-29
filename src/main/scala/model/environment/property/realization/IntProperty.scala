package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.environment.property.GaussianFilterBuilder.function3d
import scala.model.environment.property.Property
import scala.model.environment.property.realization.IntProperty.filter
import scala.utility.MathHelper.intValueOf
import scala.utility.IterableHelper.RichIterable

/**
 * Represent a generic property who works with data of type Int.
 * Simplify the use (and help DRY) of numeric properties
 */
private[realization] trait IntProperty extends Property with IntRange {
  override type StateType = IntegerState
  override type VariationType = IntegerVariation

  def default: Int

  implicit def state(_value: Int): IntegerState = new IntegerState {
    override val value: Int = limit(_value)
  }

  /** A partial implementation of a state for an integer-type property */
  trait IntegerState extends State {

    override def numericRepresentation(percentage: Boolean = true): Int = percentage match {
      case true => (value - minValue) * 100 / rangeWidth
      case _ => value
    }

    override def equals(a: Any): Boolean = a.isInstanceOf[IntegerState] && a.asInstanceOf[IntegerState].value == value
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

  override def generateFilter(xDecrement: Int, yDecrement: Int): DenseMatrix[VariationType] =
    filter(xDecrement, yDecrement)(zeroCenteredRange._1, zeroCenteredRange._2).map(variation(_))
}

private[realization] object IntProperty {

  def filter(xDecrement: Int, yDecrement: Int)(implicit minValue: Int, maxValue: Int): DenseMatrix[Double] =
    function3d((minValue to maxValue).filter(_ != 0).random.get, 0, xDecrement, yDecrement)
}