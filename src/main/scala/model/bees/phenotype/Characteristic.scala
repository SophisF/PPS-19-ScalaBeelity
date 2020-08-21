package scala.model.bees.phenotype

import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

object Characteristic {

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
  case class TemperatureCompatibilityCharacteristic(influenceValue: Double)
                                                   (implicit mapper: Int => Int => Double => Int => (Int, Int))
    extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY
    override val expression: (Int, Int) = mapper(20)(34)(influenceValue)(2)

  }

  case class HumidityCompatibilityCharacteristic(influenceValue: Double)
                                                (implicit mapper: Int => Int => Double => Int => (Int, Int))
    extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY
    override val expression: (Int, Int) = mapper(40)(80)(influenceValue)(8)
  }

  case class PressureCompatibilityCharacteristic(influenceValue: Double)
                                                (implicit mapper: Int => Int => Double => Int => (Int, Int))
    extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.PRESSURE_COMPATIBILITY
    override val expression: (Int, Int) = mapper(1000)(1050)(influenceValue)(5)
  }

  case class AggressionRateCharacteristic(influenceValue: Double)(implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.AGGRESSION_RATE
    override val expression: Int = mapper(1)(10)(influenceValue)
  }

  case class ReproductionRateCharacteristic(influenceValue: Double)(implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.REPRODUCTION_RATE
    override val expression: Int = mapper(1)(5)(influenceValue)
  }

  case class LongevityRateCharacteristic(influenceValue: Double)
                                        (implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.LONGEVITY
    override val expression: Int = mapper(30)(120)(influenceValue)
  }

  case class ColorRateCharacteristic(influenceValue: Double) extends Characteristic with DoubleExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.COLOR
    override val expression: Double = influenceValue
  }

  case class SpeedRateCharacteristic(influenceValue: Double)
                                    (implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.SPEED
    override val expression: Int = mapper(1)(2)(influenceValue)
  }


}
