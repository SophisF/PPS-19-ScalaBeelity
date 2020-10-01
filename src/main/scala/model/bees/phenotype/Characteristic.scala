package scala.model.bees.phenotype

import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.bees.phenotype.ExpressionMapper._

/**
 * Object that represents a characteristic.
 */
object Characteristic {

  /**
   * Implicit method to convert an expression to a tuple of Int.
   *
   * @param expression the expression.
   * @return a tuple with an expression range.
   */
  implicit def toTuple(expression: Characteristic#Expression): (Int, Int) = expression.asInstanceOf[(Int, Int)]

  /**
   * Implicit method to convert an expression to a Int.
   *
   * @param expression the expression.
   * @return a Int expression.
   */
  implicit def toInt(expression: Characteristic#Expression): Int = expression.asInstanceOf[Int]

  /**
   * Implicit method to convert an expression to Double.
   *
   * @param expression the expression.
   * @return a Double expression.
   */
  implicit def toDouble(expression: Characteristic#Expression): Double = expression.asInstanceOf[Double]

  /**
   * Implementation of apply as a factory
   *
   * @param taxonomy       the taxonomy of the characteristic.
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
    val taxonomy: CharacteristicTaxonomy
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

  /**
   * Case object for the temperature characteristic.
   */
  case object TemperatureCompatibilityCharacteristic {
    val min = 18
    val max = 36
    val rangeTuning = 2

    /**
     * Apply method for the temperature characteristic.
     *
     * @param influenceValue the value of influence of the expressed genes.
     * @return a new TemperatureCompatibilityCharacteristic.
     */
    def apply(influenceValue: Double): Characteristic with RangeExpression = TemperatureCompatibilityCharacteristic(influenceValue)

    /**
     * Class that represents temperature characteristic.
     *
     * @param influenceValue a double value that represents influence value.
     * @param mapper         a implicit mapper to calculate the expression.
     */
    private case class TemperatureCompatibilityCharacteristic(influenceValue: Double)
                                                             (implicit mapper: Int => Int => Double => Int => (Int, Int))
      extends Characteristic with RangeExpression {
      override val taxonomy: CharacteristicTaxonomy = CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY
      override val expression: (Int, Int) = mapper(min)(max)(influenceValue)(rangeTuning)
    }

  }

  /**
   * Case object for the temperature characteristic.
   */
  case object HumidityCompatibilityCharacteristic {
    val min = 40
    val max = 80
    val rangeTuning = 3

    /**
     * Apply method for the humidity characteristic.
     *
     * @param influenceValue the value of influence of the expressed genes.
     * @return a new HumidityCompatibilityCharacteristic.
     */
    def apply(influenceValue: Double): Characteristic with RangeExpression = HumidityCompatibilityCharacteristic(influenceValue)

    /**
     * Class that represents humidity characteristic.
     *
     * @param influenceValue a double value that represents influence value.
     * @param mapper         a implicit mapper to calculate the expression.
     */
    private case class HumidityCompatibilityCharacteristic(influenceValue: Double)
                                                          (implicit mapper: Int => Int => Double => Int => (Int, Int))
      extends Characteristic with RangeExpression {
      override val taxonomy: CharacteristicTaxonomy = CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY
      override val expression: (Int, Int) = mapper(min)(max)(influenceValue)(rangeTuning)
    }

  }

  /**
   * Case object for the temperature characteristic.
   */
  case object PressureCompatibilityCharacteristic {
    val min = 1000
    val max = 1050
    val rangeTuning = 5

    /**
     * Apply method for the pressure characteristic.
     *
     * @param influenceValue the value of influence of the expressed genes.
     * @return a new PressureCompatibilityCharacteristic.
     */
    def apply(influenceValue: Double): Characteristic with RangeExpression = PressureCompatibilityCharacteristic(influenceValue)

    /**
     * Class that represents pressure characteristic.
     *
     * @param influenceValue a double value that represents influence value.
     * @param mapper         a implicit mapper to calculate the expression.
     */
    private case class PressureCompatibilityCharacteristic(influenceValue: Double)
                                                          (implicit mapper: Int => Int => Double => Int => (Int, Int))
      extends Characteristic with RangeExpression {
      override val taxonomy: CharacteristicTaxonomy = CharacteristicTaxonomy.PRESSURE_COMPATIBILITY
      override val expression: (Int, Int) = mapper(min)(max)(influenceValue)(rangeTuning)
    }

  }

  /**
   * Case object for the aggression characteristic.
   */
  case object AggressionRateCharacteristic {
    val min = 1
    val max = 10

    /**
     * Apply method for the aggression characteristic.
     *
     * @param influenceValue the value of influence of the expressed genes.
     * @return a new AggressionRateCharacteristic.
     */
    def apply(influenceValue: Double): Characteristic with IntExpression = AggressionRateCharacteristic(influenceValue)

    /**
     * Class that represents aggression rate characteristic.
     *
     * @param influenceValue a double value that represents influence value.
     * @param mapper         a implicit mapper to calculate the expression.
     */
    private case class AggressionRateCharacteristic(influenceValue: Double)(implicit mapper: Int => Int => Double => Int)
      extends Characteristic with IntExpression {
      override val taxonomy: CharacteristicTaxonomy = CharacteristicTaxonomy.AGGRESSION_RATE
      override val expression: Int = mapper(min)(max)(influenceValue)
    }

  }

  /**
   * Case object for the reproduction characteristic.
   */
  case object ReproductionRateCharacteristic {
    val min = 1
    val max = 5

    /**
     * Apply method for the reproduction rate characteristic.
     *
     * @param influenceValue the value of influence of the expressed genes.
     * @return a new ReproductionRateCharacteristic.
     */
    def apply(influenceValue: Double): Characteristic with IntExpression = ReproductionRateCharacteristic(influenceValue)

    /**
     * Class that represents reproduction rate characteristic.
     *
     * @param influenceValue a double value that represents influence value.
     * @param mapper         a implicit mapper to calculate the expression.
     */
    private case class ReproductionRateCharacteristic(influenceValue: Double)(implicit mapper: Int => Int => Double => Int)
      extends Characteristic with IntExpression {
      override val taxonomy: CharacteristicTaxonomy = CharacteristicTaxonomy.REPRODUCTION_RATE
      override val expression: Int = mapper(min)(max)(influenceValue)
    }

  }

  /**
   * Case object for the longevity characteristic.
   */
  case object LongevityRateCharacteristic {
    val min = 30
    val max = 90

    /**
     * Apply method for the reproduction rate characteristic.
     *
     * @param influenceValue the value of influence of the expressed genes.
     * @return a new ReproductionRateCharacteristic.
     */
    def apply(influenceValue: Double): Characteristic with IntExpression = LongevityRateCharacteristic(influenceValue)

    /**
     * Class that represents longevity rate characteristic.
     *
     * @param influenceValue a double value that represents influence value.
     * @param mapper         a implicit mapper to calculate the expression.
     */
    private case class LongevityRateCharacteristic(influenceValue: Double)
                                                  (implicit mapper: Int => Int => Double => Int)
      extends Characteristic with IntExpression {
      override val taxonomy: CharacteristicTaxonomy = CharacteristicTaxonomy.LONGEVITY
      override val expression: Int = mapper(min)(max)(influenceValue)
    }

  }

  /**
   * Case object for the speed characteristic.
   */
  case object SpeedRateCharacteristic {
    val min = 1
    val max = 2

    /**
     * Apply method for the speed rate characteristic.
     *
     * @param influenceValue the value of influence of the expressed genes.
     * @return a new SpeedRateCharacteristic.
     */
    def apply(influenceValue: Double): Characteristic with IntExpression = SpeedRateCharacteristic(influenceValue)

    /**
     * Class that represents speed rate characteristic.
     *
     * @param influenceValue a double value that represents influence value.
     * @param mapper         a implicit mapper to calculate the expression.
     */
    private case class SpeedRateCharacteristic(influenceValue: Double)
                                              (implicit mapper: Int => Int => Double => Int)
      extends Characteristic with IntExpression {
      override val taxonomy: CharacteristicTaxonomy = CharacteristicTaxonomy.SPEED
      override val expression: Int = mapper(min)(max)(influenceValue)
    }

  }


}
