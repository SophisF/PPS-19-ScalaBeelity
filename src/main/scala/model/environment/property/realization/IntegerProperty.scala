package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.environment.property.{FilterBuilder, Property, Range}
import scala.model.environment.utility.SequenceHelper.RichSequence
import scala.model.environment.utility.MathHelper._

/**
 * Simplify the use (and help DRY) of numeric properties
 *
 * @author Paolo Baldini
 */
trait IntegerProperty extends Property with Range {
  override type ValueType = Int
  override type StateType = IntegerState
  override type VariationType = IntegerVariation

  def default: Int

  implicit def state(_value: Int = default): IntegerState = new IntegerState { override val value: Int = limit(_value) }

  trait IntegerState extends State {

    override def numericRepresentation(percentage: Boolean = true): Int = percentage match {
      case true => (value - minValue) * 100 / (maxValue - minValue)
      case _ => value
    }
  }

  implicit def variation(_value: Int): VariationType = new IntegerVariation { override def value: Int = _value }

  trait IntegerVariation extends Variation {

    def value: Int

    def isNull: Boolean = value == 0

    override def vary[S <: State](_state: S): StateType = state(_state.value + value)
  }

  override def generateFilter(xDecrement: Int, yDecrement: Int): DenseMatrix[VariationType] =
    filter(xDecrement, yDecrement).map(variation(_))

  private def filter(xDecrement: Int, yDecrement: Int): DenseMatrix[Double] = FilterBuilder
    .gaussianFunction3d((minValue to maxValue).filter(_ != 0).random().get, 0, xDecrement, yDecrement)
}