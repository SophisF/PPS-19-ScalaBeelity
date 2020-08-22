package scala.model.bees.phenotype

import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.bees.phenotype.ExpressionMapper._

object Characteristic {

  def apply(taxonomy: CharacteristicTaxonomy, influenceValue: Double): Characteristic = {
    taxonomy match {
      case CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY => TemperatureCompatibilityCharacteristic(influenceValue)
      case CharacteristicTaxonomy.PRESSURE_COMPATIBILITY => PressureCompatibilityCharacteristic( influenceValue)
      case CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY => HumidityCompatibilityCharacteristic(influenceValue)
      case CharacteristicTaxonomy.AGGRESSION_RATE => AggressionRateCharacteristic(influenceValue)
      case CharacteristicTaxonomy.REPRODUCTION_RATE => ReproductionRateCharacteristic(influenceValue)
      case CharacteristicTaxonomy.LONGEVITY => LongevityRateCharacteristic(influenceValue)
      case CharacteristicTaxonomy.COLOR => ColorRateCharacteristic(influenceValue)
      case _ => SpeedRateCharacteristic( influenceValue)
    }
  }

  //def getExpression(characteristic: Characteristic with IntExpression): characteristic.Expression = characteristic.expression
  //possibly adding a generic type and an Expression Interface as superclasses of the others.
  trait Characteristic {
    type Expression
    val name: CharacteristicTaxonomy
    val expression: Expression
  }

  //Int or Double??
  trait RangeExpression {
    characteristic: Characteristic =>
    type Expression = (Int, Int)

  }

  trait DoubleExpression {
    characteristic: Characteristic =>
    type Expression = Double
  }

  trait IntExpression {
    characteristic: Characteristic =>
    type Expression = Int
  }

  //così vanno dai 18 ai 36 gradi
  private case class TemperatureCompatibilityCharacteristic(influenceValue: Double)
                                                   (implicit mapper: Int => Int => Double => Int => (Int, Int))
    extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY
    override val expression: (Int, Int) = mapper(20)(34)(influenceValue)(2)

  }

  private case class HumidityCompatibilityCharacteristic(influenceValue: Double)
                                                (implicit mapper: Int => Int => Double => Int => (Int, Int))
    extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY
    override val expression: (Int, Int) = mapper(40)(80)(influenceValue)(8)
  }

  private case class PressureCompatibilityCharacteristic(influenceValue: Double)
                                                (implicit mapper: Int => Int => Double => Int => (Int, Int))
    extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.PRESSURE_COMPATIBILITY
    override val expression: (Int, Int) = mapper(1000)(1050)(influenceValue)(5)
  }

  private case class AggressionRateCharacteristic(influenceValue: Double)(implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.AGGRESSION_RATE
    override val expression: Int = mapper(1)(10)(influenceValue)
  }

  private case class ReproductionRateCharacteristic(influenceValue: Double)(implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.REPRODUCTION_RATE
    override val expression: Int = mapper(1)(5)(influenceValue)
  }

  private case class LongevityRateCharacteristic(influenceValue: Double)
                                        (implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.LONGEVITY
    override val expression: Int = mapper(30)(120)(influenceValue)
  }

  private case class ColorRateCharacteristic(influenceValue: Double) extends Characteristic with DoubleExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.COLOR
    override val expression: Double = influenceValue
  }

  private case class SpeedRateCharacteristic(influenceValue: Double)
                                    (implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.SPEED
    override val expression: Int = mapper(1)(2)(influenceValue)
  }


}
