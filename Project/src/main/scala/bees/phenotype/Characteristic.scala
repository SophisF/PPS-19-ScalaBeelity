package bees.phenotype

import bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import utility.ExpressionMapper

/**
 * Object that represents a characteristic.
 */
object Characteristic {

  //possibly adding a generic type and an Expression Interface as superclasses of the others.
  /**
   * Trait for a characteristic
   */
  trait Characteristic{
    type Expression
    val name: CharacteristicTaxonomy
    val expression: Expression
  }

  /**
   * Create a new characteristic
   * @param taxonomy wrap a characteristic taxonomy
   * @param averageExpressions average of all characteristic's expressions
   * @param influenceValue value of influence for each characteristic
   * @return a new characteristic
   */
  def apply(taxonomy: CharacteristicTaxonomy, averageExpressions: Double, influenceValue: Double ): Characteristic = {
    taxonomy match {
      case CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY => TemperatureCompatibilityCharacteristic(averageExpressions, influenceValue)
      case CharacteristicTaxonomy.PRESSURE_COMPATIBILITY => PressureCompatibilityCharacteristic(averageExpressions, influenceValue)
      case CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY => HumidityCompatibilityCharacteristic(averageExpressions, influenceValue)
      case CharacteristicTaxonomy.AGGRESSION_RATE => AggressionRateCharacteristic(averageExpressions, influenceValue)
      case CharacteristicTaxonomy.REPRODUCTION_RATE => ReproductionRateCharacteristic(averageExpressions, influenceValue)
      case CharacteristicTaxonomy.LONGEVITY => LongevityRateCharacteristic(averageExpressions, influenceValue)
      case CharacteristicTaxonomy.COLOR => ColorRateCharacteristic(averageExpressions, influenceValue)
      case _ => SpeedRateCharacteristic(averageExpressions, influenceValue)
    }
  }

  //Int or Double??
  /**
   * Trait that represents range expression
   */
  trait RangeExpression {
    characteristic: Characteristic => type Expression = (Int, Int)
    val rangeTuning: Int
  }

  /**
   * Trait that represents double expression
   */
  trait DoubleExpression{
    characteristic: Characteristic => type Expression = Double
  }

  /**
   * Trait that represents int expression
   */
  trait IntExpression{
    characteristic: Characteristic => type Expression = Int
  }

  /**
   * Class that represents the temperature compatibility characteristic
   * @param averageExpressions average of all characteristic's expressions
   * @param influenceValue value of influence for temperature compatibility characteristic
   */
  case class TemperatureCompatibilityCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY
    override val expression: (Int, Int) = ExpressionMapper.getRange(18, 36, averageExpressions, influenceValue, rangeTuning)
    override val rangeTuning: Int = 5
  }

  /**
   * Class that represents the humidity compatibility characteristic
   * @param averageExpressions average of all characteristic's expressions
   * @param influenceValue value of influence for humidity compatibility characteristic
   */
  case class HumidityCompatibilityCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY
    override val expression: (Int, Int) = ExpressionMapper.getRange(40, 80, averageExpressions , influenceValue, rangeTuning)
    override val rangeTuning: Int = 5
  }

  /**
   * Class that represents the pressure compatibility characteristic
   * @param averageExpressions average of all characteristic's expressions
   * @param influenceValue value of influence for pressure compatibility characteristic
   */
  case class PressureCompatibilityCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.PRESSURE_COMPATIBILITY
    override val expression: (Int, Int) = ExpressionMapper.getRange(1000, 1050, averageExpressions, influenceValue, rangeTuning)
    override val rangeTuning: Int = 5
  }

  /**
   * Class that represents the aggression rate characteristic
   * @param averageExpressions average of all characteristic's expressions
   * @param influenceValue value of influence for aggression rate characteristic
   */
  case class AggressionRateCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with IntExpression{
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.AGGRESSION_RATE
    override val expression: Int = math.round(influenceValue).toInt
  }

  /**
   * Class that represents the reproduction rate characteristic
   * @param averageExpressions average of all characteristic's expressions
   * @param influenceValue value of influence for reproduction rate characteristic
   */
  case class ReproductionRateCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with IntExpression{
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.REPRODUCTION_RATE
    override val expression: Int = math.round(influenceValue).toInt
  }

  /**
   * Class that represents the longevity rate characteristic
   * @param averageExpressions average of all characteristic's expressions
   * @param influenceValue value of influence for longevity rate characteristic
   */
  case class LongevityRateCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with IntExpression{
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.LONGEVITY
    override val expression: Int = ExpressionMapper.getRange(120, averageExpressions, influenceValue )
  }

  /**
   * Class that represents the color rate characteristic
   * @param averageExpressions average of all characteristic's expressions
   * @param influenceValue value of influence for color rate characteristic
   */
  case class ColorRateCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with DoubleExpression{
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.COLOR
    override val expression: Double = influenceValue
  }

  /**
   * Class that represents the speed rate characteristic
   * @param averageExpressions average of all characteristic's expressions
   * @param influenceValue value of influence for speed rate characteristic
   */
  case class SpeedRateCharacteristic(averageExpressions: Double, influenceValue:Double) extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.SPEED
    override val expression: Int = ExpressionMapper.getRange(averageExpressions, influenceValue)
  }

}
