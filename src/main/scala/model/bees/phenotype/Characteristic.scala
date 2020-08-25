package scala.model.bees.phenotype

import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.bees.phenotype.ExpressionMapper._

/**
 * Object that represents a characteristic.
 */
object Characteristic {

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
      case CharacteristicTaxonomy.COLOR => ColorRateCharacteristic(influenceValue)
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
    type Expression = (Int, Int) //Int or Double??
  }

  /**
   * Different definition of the abstract type expression as double value.
   */
  trait DoubleExpression {
    characteristic: Characteristic =>
    type Expression = Double
  }

  /**
   * Different definition of the abstract type expression as int value.
   */
  trait IntExpression {
    characteristic: Characteristic =>
    type Expression = Int
  }

  //TODO non mi piace min e max siano diversi dai valori inseriti
  /**
   * Class that represents temperature characteristic.
   * @param influenceValue a double value that represents influence value.
   * @param mapper a implicit mapper to calculate the expression.
   */
  private case class TemperatureCompatibilityCharacteristic(influenceValue: Double)
                                                   (implicit mapper: Int => Int => Double => Int => (Int, Int))
    extends Characteristic with RangeExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY
    override val expression: (Int, Int) = mapper(20)(34)(influenceValue)(2) //così vanno dai 18 ai 36 gradi
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
    override val expression: (Int, Int) = mapper(40)(70)(influenceValue)(3) // Sophi - mapper(40)(80)(influenceValue)(8)
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
    override val expression: (Int, Int) = mapper(1000)(1050)(influenceValue)(5)
  }

  /**
   * Class that represents aggression rate characteristic.
   * @param influenceValue a double value that represents influence value.
   * @param mapper a implicit mapper to calculate the expression.
   */
  private case class AggressionRateCharacteristic(influenceValue: Double)(implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.AGGRESSION_RATE
    override val expression: Int = mapper(1)(10)(influenceValue)
  }

  /**
   * Class that represents reproduction rate characteristic.
   * @param influenceValue a double value that represents influence value.
   * @param mapper a implicit mapper to calculate the expression.
   */
  private case class ReproductionRateCharacteristic(influenceValue: Double)(implicit mapper: Int => Int => Double => Int)
    extends Characteristic with IntExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.REPRODUCTION_RATE
    override val expression: Int = mapper(1)(5)(influenceValue)
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
    override val expression: Int = mapper(30)(120)(influenceValue)
  }

  /**
   * Class that represents color characteristic.
   * @param influenceValue a double value that represents influence value.
   */
  private case class ColorRateCharacteristic(influenceValue: Double) extends Characteristic with DoubleExpression {
    override val name: CharacteristicTaxonomy = CharacteristicTaxonomy.COLOR
    override val expression: Double = influenceValue
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
    override val expression: Int = mapper(1)(2)(influenceValue)
  }

}
