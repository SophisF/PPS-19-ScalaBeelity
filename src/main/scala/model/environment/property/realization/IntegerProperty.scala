package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.environment.property.GaussianFilterBuilder.function3d
import scala.model.environment.property.{Property, Range}
import scala.utility.MathHelper._
import scala.utility.IterableHelper.RichIterable

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

  implicit def state(_value: Int): IntegerState = new IntegerState {
    override val value: Int = limit(_value)
  }

  trait IntegerState extends State {

    override def numericRepresentation(percentage: Boolean = true): Int = percentage match {
      case true => (value - minValue) * 100 / (maxValue - minValue)
      case _ => value
    }

    override def equals(obj: Any): Boolean = obj.isInstanceOf[IntegerState] &&
      obj.asInstanceOf[IntegerState].value == value
  }

  implicit def variation(_value: Int): VariationType = new IntegerVariation {
    override def value: Int = _value
  }

  trait IntegerVariation extends Variation {

    def value: Int

    def isNull: Boolean = value == 0

    override def vary[S <: State](_state: S): StateType = state(_state.value + value)
  }


  //TODO: Applicare principio DRY e rimuovere ripetizione.
  private def rangeWidth: Int = maxValue - minValue

  private def rangeCenter: Int = minValue + rangeWidth / 2

  private def zeroCenteredRange: (Int, Int) = (minValue - rangeCenter, maxValue - rangeCenter)

  override def generateFilter(xDecrement: Int, yDecrement: Int): DenseMatrix[VariationType] =
    IntegerProperty.filter(xDecrement, yDecrement)(zeroCenteredRange._1, zeroCenteredRange._2).map(variation(_))
}

object IntegerProperty {

  def filter(xDecrement: Int, yDecrement: Int)(implicit minValue: Int, maxValue: Int): DenseMatrix[Double] =
    function3d((minValue to maxValue).filter(_ != 0).random().get, 0, xDecrement, yDecrement)
}