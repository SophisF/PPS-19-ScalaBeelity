package scala.model.bees.phenotype

import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.bees.phenotype.ExpressionMapper._

/**
 * Object that represents a characteristic.
 */
object Characteristic {

  /**
   * Implicit method to convert an expression to a tuple of Int.
   * @param expression the expression.
   * @return a tuple with an expression range.
   */
  implicit def toTuple(expression: Characteristic#Expression): (Int, Int) = expression.asInstanceOf[(Int, Int)]

  /**
   * Implicit method to convert an expression to a Int.
   * @param expression the expression.
   * @return a Int expression.
   */
  implicit def toInt(expression: Characteristic#Expression): Int = expression.asInstanceOf[Int]

  /**
   * Implicit method to convert an expression to Double.
   * @param expression the expression.
   * @return a Double expression.
   */
  implicit def toDouble(expression: Characteristic#Expression): Double = expression.asInstanceOf[Double]

  /**
   * Implementation of apply as a factory
   * @param taxonomy the taxonomy of the characteristic.
   * @param influenceValue the influence value matches with the taxonomy.
   * @return the characteristic that corresponds to the taxonomy and the influence value.
   */
  def apply(taxonomy: CharacteristicTaxonomy, influenceValue: Double): Characteristic = {
    taxonomy match {
      case CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY => TemperatureCompatibilityCharacteristic(influenceValue)
      case CharacteristicTaxonomy.PRESSURE_COMPATIBILITY => PressureCompatibilityCharacteristic(influenceValue)
      case CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY => HumidityCompatibilityCharacteristic(influenceValue)
      case CharacteristicTaxonomy.AGGRESSION_RATE => AggressionRateCharacteristic(influenceValue)
      case CharacteristicTaxonomy.REPRODUCTION_RATE => ReproductionRateCharacteristic(influenceValue)
      case CharacteristicTaxonomy.LONGEVITY => LongevityRateCharacteristic(influenceValue)
      case _ => SpeedRateCharacteristic(influenceValue)
    }
  }

  /**
   * Trait that represents a characteristic.
   */
  trait Characteristic {
    type Expression
    val name: CharacteristicTaxonomy
    val expression: Expression
  }

  /**
   * Different definition of the abstract type expression as range of int value.
   */
  trait RangeExpression {
    characteristic: Characteristic =>
    type Expression = (Int, Int)
  }

  /**
   * Different definition of the abstract type expression as int value.
   */
  trait IntExpression {
    characteristic: Characteristic =>
    type Expression = Int
  }

  case object TemperatureCharacteristicObj {
    val min = 18
    val max = 36
    val rangeTuning = 2
  }

  case object HumidityCharacteristicObj {
    val min = 40
    val max = 80
    val rangeTuning = 3
  }

  case object PressureCharacteristicObj {
    val min = 1000
    val max = 1050
    val rangeTuning = 5
  }

  case object AggressionCharacteristicObj {
    val min = 1
    val max = 10
  }

  case object ReproductionCharacteristicObj {
    val min = 1
    val max = 10
  }

  case object LongevityCharacteristicObj {
    val min = 30
    val max = 120
  }
  
  case object SpeedCharacteristicObj {
    val min = 1
    val max = 2
  }

  /**
   * Class that represents temperature characteristic.
   * @param influenceValue a double value that represents influence value.
   * @param mapper a implicit mapper to calculate the expression.
   */
  private case class TemperatureCompatibilityCharacteristic(influenceValue: Double)
                                                   (implicit mapper: Int => Int => Double => Int => (Int, Int))
    extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY
    override val expression: (Int, Int) = mapper(TemperatureCharacteristicObj.min)(TemperatureCharacteristicObj.max)(influenceValue)(TemperatureCharacteristicObj.rangeTuning)
  }

  /**
   * Class that represents humidity characteristic.
   * @param influenceValue a double value that represents influence value.
   * @param mapper a implicit mapper to calculate the expression.
   */
  private case class HumidityCompatibilityCharacteristic(influenceValue: Double)
                                                (implicit mapper: Int => Int => Double => Int => (Int, Int))
    extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY
    override val expression: (Int, Int) = mapper(HumidityCharacteristicObj.min)(HumidityCharacteristicObj.max)(influenceValue)(HumidityCharacteristicObj.rangeTuning)
  }

  /**
   * Class that represents pressure characteristic.
   * @param influenceValue a double value that represents influence value.
   * @param mapper a implicit mapper to calculate the expression.
   */
  private case class PressureCompatibilityCharacteristic(influenceValue: Double)
                                                (implicit mapper: Int => Int => Double => Int => (Int, Int))
    extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.PRESSURE_COMPATIBILITY
    override val expression: (Int, Int) = mapper(PressureCharacteristicObj.min)(PressureCharacteristicObj.max)(influenceValue)(PressureCharacteristicObj.rangeTuning)
  }

  /**
   * Class that represents aggression rate characteristic.
   * @param influenceValue a double value that represents influence value.
   * @param mapper a implicit mapper to calculate the expression.
   */
  private case class AggressionRateCharacteristic(influenceValue: Double)(implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.AGGRESSION_RATE
    override val expression: Int = mapper(AggressionCharacteristicObj.min)(AggressionCharacteristicObj.max)(influenceValue)
  }

  /**
   * Class that represents reproduction rate characteristic.
   * @param influenceValue a double value that represents influence value.
   * @param mapper a implicit mapper to calculate the expression.
   */
  private case class ReproductionRateCharacteristic(influenceValue: Double)(implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.REPRODUCTION_RATE
    override val expression: Int = mapper(ReproductionCharacteristicObj.min)(ReproductionCharacteristicObj.max)(influenceValue)
  }

  /**
   * Class that represents longevity rate characteristic.
   * @param influenceValue a double value that represents influence value.
   * @param mapper a implicit mapper to calculate the expression.
   */
  private case class LongevityRateCharacteristic(influenceValue: Double)
                                        (implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.LONGEVITY
    override val expression: Int = mapper(LongevityCharacteristicObj.min)(LongevityCharacteristicObj.max)(influenceValue)
  }

  /**
   * Class that represents speed rate characteristic.
   * @param influenceValue a double value that represents influence value.
   * @param mapper a implicit mapper to calculate the expression.
   */
  private case class SpeedRateCharacteristic(influenceValue: Double)
                                    (implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.SPEED
    override val expression: Int = mapper(SpeedCharacteristicObj.min)(SpeedCharacteristicObj.max)(influenceValue)
  }

}
