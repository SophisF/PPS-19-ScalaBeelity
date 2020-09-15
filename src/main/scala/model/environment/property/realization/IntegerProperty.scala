package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.environment.property.{FilterBuilder, Range, TimeDependentProperty}
import scala.model.environment.time.Time
import scala.model.environment.utility.SequenceHelper.RichSequence

/**
 * Simplify the use (and help DRY) of numeric properties
 *
 * @author Paolo Baldini
 */
trait IntegerProperty extends TimeDependentProperty with Range {
  override type ValueType = Int

  trait IntegerState extends State {
    override def numericRepresentation: Int = (value - minValue) * 100 / (maxValue - minValue)
  }

  trait IntegerVariation extends Variation {

    def value: Int

    def isNull: Boolean = value == 0
  }

  trait IntegerTimedVariation extends TimedVariation {
    private var evaluated: Double = 0

    def instantaneous(value: Int, start: Time, duration: Time, instant: Time = Time.now()): Int = {
      val percentage = (instant - start) / duration.toDouble - evaluated
      if (percentage * value > 0) evaluated += percentage
      percentage * value
    }
  }

  implicit def variation(value: Int): Variation

  override def generateFilter(xDecrement: Int, yDecrement: Int): DenseMatrix[Variation] =
    filter(xDecrement, yDecrement).map(variation(_))

  def timedVariation(value: Int, start: Time, duration: Time): TimedVariation

  override def timedFilter(xDecrement: Int, yDecrement: Int, start: Time, duration: Time)
  : DenseMatrix[TimedVariation] = filter(xDecrement, yDecrement).map(timedVariation(_, start, duration))

  private def filter(xDecrement: Int, yDecrement: Int): DenseMatrix[Double] = FilterBuilder
    .gaussianFunction3d((minValue to maxValue).filter(_ != 0).random().get, 0, xDecrement, yDecrement)

  /**
   * Convert generic numeric value (e.g. Double, Float) to ValueType (Int)
   *
   * @param value to convert to ValueType
   * @tparam N type of value
   * @return converted value
   */
  implicit def intValue[N: Numeric](value: N): Int = implicitly[Numeric[N]].toInt(value)
}