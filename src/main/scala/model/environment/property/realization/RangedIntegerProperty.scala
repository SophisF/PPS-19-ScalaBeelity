package scala.model.environment.property.realization

import breeze.linalg.DenseMatrix

import scala.model.environment.property.{FilterBuilder, Property, Range}
import scala.util.Random

/**
 * Simplify the use (and help DRY) of numeric properties
 *
 * @author Paolo Baldini
 */
trait RangedIntegerProperty extends Property with Range {
  override type ValueType = Int

  trait RangedIntegerState extends State {
    override def numericRepresentation: Int = (value - minValue) * 100 / (maxValue - minValue)
  }

  def variation(value: Int): Variation

  override def generateFilter(xDecrement: Int, yDecrement: Int): DenseMatrix[Variation] = FilterBuilder
    .gaussianFunction3d((minValue to maxValue).filter(_ != 0)(Random.nextInt(5)), 1, xDecrement, yDecrement)
    .mapValues(variation(_))

  /**
   * Convert generic numeric value (e.g. Double, Float) to ValueType (Int)
   *
   * @param value to convert to ValueType
   * @tparam N type of value
   * @return converted value
   */
  implicit def intValue[N: Numeric](value: N): Int = implicitly[Numeric[N]].toInt(value)
}