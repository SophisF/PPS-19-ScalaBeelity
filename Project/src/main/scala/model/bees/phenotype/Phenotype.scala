package scala.model.bees.phenotype

import scala.collection.immutable.HashSet
import scala.model.bees.phenotype.Characteristic.{AggressionRateCharacteristic, Characteristic, ColorRateCharacteristic, DoubleExpression, HumidityCompatibilityCharacteristic, IntExpression, LongevityRateCharacteristic, PressureCompatibilityCharacteristic, RangeExpression, ReproductionRateCharacteristic, SpeedRateCharacteristic, TemperatureCompatibilityCharacteristic}
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.bees.phenotype.ExpressionMapper._

object Phenotype {

  trait Phenotype {

    val environmentalCharacteristics: Set[Characteristic with RangeExpression]

    val temperatureCompatibility: Characteristic with RangeExpression

    val pressureCompatibility: Characteristic with RangeExpression

    val humidityCompatibility: Characteristic with RangeExpression

    val aggression: Characteristic with IntExpression

    val reproductionRate: Characteristic with IntExpression

    val longevity: Characteristic with IntExpression

    val speed: Characteristic with IntExpression

    val color: Characteristic with DoubleExpression

  }

  case class PhenotypeImpl(expressions: Map[CharacteristicTaxonomy, Double]) extends Phenotype {
    require(expressions.size == CharacteristicTaxonomy.maxId)
    override val temperatureCompatibility: Characteristic with RangeExpression = TemperatureCompatibilityCharacteristic(expressions(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY))
    override val pressureCompatibility: Characteristic with RangeExpression = PressureCompatibilityCharacteristic(expressions(CharacteristicTaxonomy.PRESSURE_COMPATIBILITY))
    override val humidityCompatibility: Characteristic with RangeExpression = HumidityCompatibilityCharacteristic(expressions(CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY))
    override val aggression: Characteristic with IntExpression = AggressionRateCharacteristic(expressions(CharacteristicTaxonomy.AGGRESSION_RATE))
    override val reproductionRate: Characteristic with IntExpression = ReproductionRateCharacteristic(expressions(CharacteristicTaxonomy.REPRODUCTION_RATE))
    override val longevity: Characteristic with IntExpression = LongevityRateCharacteristic(expressions(CharacteristicTaxonomy.LONGEVITY))
    override val speed: Characteristic with IntExpression = SpeedRateCharacteristic(expressions(CharacteristicTaxonomy.SPEED))
    override val color: Characteristic with DoubleExpression = ColorRateCharacteristic(expressions(CharacteristicTaxonomy.COLOR))
    override val environmentalCharacteristics: Set[Characteristic with RangeExpression] = HashSet(temperatureCompatibility, pressureCompatibility, humidityCompatibility)
  }

}
