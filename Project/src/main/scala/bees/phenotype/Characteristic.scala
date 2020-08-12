package bees.phenotype

import bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import utility.ExpressionMapper

object Characteristic {

  trait Characteristic{
    type Expression
    val name: CharacteristicTaxonomy
    val expression: Expression
  }

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
  case class TemperatureCompatibilityCharacteristic(override val name: CharacteristicTaxonomy, influenceValue: Double) extends Characteristic with RangeExpression {
    override val expression: (Int, Int) = ??? //ExpressionMapper.getRange(18, 36, , influenceValue, rangeTuning)
    override val rangeTuning: Int = 5
  }

  case class HumidityCompatibilityCharacteristic(override val name: CharacteristicTaxonomy, influenceValue: Double) extends Characteristic with RangeExpression {
    override val expression: (Int, Int) = ??? //RangeMapper.getRange(18, 36, , influenceValue, rangeTuning)
    override val rangeTuning: Int = 5
  }

  case class PressureCompatibilityCharacteristic(override val name: CharacteristicTaxonomy, influenceValue: Double) extends Characteristic with RangeExpression {
    override val expression: (Int, Int) = ??? //RangeMapper.getRange(18, 36, , influenceValue, rangeTuning)
    override val rangeTuning: Int = 5
  }

  case class AggressionRateCharacteristic(override val name: CharacteristicTaxonomy, influenceValue: Double) extends Characteristic with IntExpression{
    override val expression: Int = math.round(influenceValue).toInt
  }

  case class ReproductionRateCharacteristic(override val name: CharacteristicTaxonomy, influenceValue: Double) extends Characteristic with IntExpression{
    override val expression: Int = math.round(influenceValue).toInt
  }

  case class LongevityRateCharacteristic(override val name: CharacteristicTaxonomy, influenceValue: Double) extends Characteristic with IntExpression{
    override val expression: Int = ??? //ExpressionMapper.getRange(90, , influenceValue )
  }

  case class ColorRateCharacteristic(override val name: CharacteristicTaxonomy, influenceValue: Double) extends Characteristic with DoubleExpression{
    override val expression: Double = influenceValue
  }


}
