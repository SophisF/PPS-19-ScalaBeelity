package bees.phenotype

import bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import utility.ExpressionMapper


object Characteristic {

  //possibly adding a generic type and an Expression Interface as superclasses of the others.
  trait Characteristic{
    type Expression
    val name: CharacteristicTaxonomy
    val expression: Expression
  }

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
  trait RangeExpression {
    characteristic: Characteristic => type Expression = (Int, Int)
    val rangeTuning: Int
  }

  trait DoubleExpression{
    characteristic: Characteristic => type Expression = Double
  }

  trait IntExpression{
    characteristic: Characteristic => type Expression = Int
  }

  //range between 18 and 36
  case class TemperatureCompatibilityCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY
    override val expression: (Int, Int) = ExpressionMapper.getRange(18, 36, averageExpressions, influenceValue, rangeTuning)
    override val rangeTuning: Int = 5
  }

  case class HumidityCompatibilityCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY
    override val expression: (Int, Int) = ExpressionMapper.getRange(40, 80, averageExpressions , influenceValue, rangeTuning)
    override val rangeTuning: Int = 5
  }

  case class PressureCompatibilityCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.PRESSURE_COMPATIBILITY
    override val expression: (Int, Int) = ExpressionMapper.getRange(1000, 1050, averageExpressions, influenceValue, rangeTuning)
    override val rangeTuning: Int = 5
  }

  case class AggressionRateCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with IntExpression{
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.AGGRESSION_RATE
    override val expression: Int = math.round(influenceValue).toInt
  }

  case class ReproductionRateCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with IntExpression{
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.REPRODUCTION_RATE
    override val expression: Int = math.round(influenceValue).toInt
  }

  case class LongevityRateCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with IntExpression{
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.LONGEVITY
    override val expression: Int = ExpressionMapper.getRange(120, averageExpressions, influenceValue )
  }

  case class ColorRateCharacteristic(averageExpressions: Double, influenceValue: Double) extends Characteristic with DoubleExpression{
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.COLOR
    override val expression: Double = influenceValue
  }

  case class SpeedRateCharacteristic(averageExpressions: Double, influenceValue:Double) extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.SPEED
    override val expression: Int = ExpressionMapper.getRange(averageExpressions, influenceValue)
  }


}
