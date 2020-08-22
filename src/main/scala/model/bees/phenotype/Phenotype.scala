package scala.model.bees.phenotype

import scala.model.bees.phenotype.Characteristic.{Characteristic, DoubleExpression, IntExpression, RangeExpression}
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

object Phenotype {

  private val defaultExpressionValue: Int = 1

  trait Phenotype {

    val characteristics: Set[Characteristic]

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
    override val characteristics: Set[Characteristic] = expressions.map(kv => Characteristic(kv._1, kv._2)).toSet

    override lazy val temperatureCompatibility: Characteristic with RangeExpression =
      this.characteristics.find(_.name.equals(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY))
        .getOrElse(Characteristic(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, defaultExpressionValue)).asInstanceOf[Characteristic with RangeExpression]
    override lazy val pressureCompatibility: Characteristic with RangeExpression =
      this.characteristics.find(_.name.equals(CharacteristicTaxonomy.PRESSURE_COMPATIBILITY))
      .getOrElse(Characteristic(CharacteristicTaxonomy.PRESSURE_COMPATIBILITY, defaultExpressionValue)).asInstanceOf[Characteristic with RangeExpression]
    override lazy val humidityCompatibility: Characteristic with RangeExpression =
      this.characteristics.find(_.name.equals(CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY))
      .getOrElse(Characteristic(CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY, defaultExpressionValue)).asInstanceOf[Characteristic with RangeExpression]
    override lazy val aggression: Characteristic with IntExpression =
      this.characteristics.find(_.name.equals(CharacteristicTaxonomy.AGGRESSION_RATE))
      .getOrElse(Characteristic(CharacteristicTaxonomy.AGGRESSION_RATE, defaultExpressionValue)).asInstanceOf[Characteristic with IntExpression]
    override lazy val reproductionRate: Characteristic with IntExpression =
      this.characteristics.find(_.name.equals(CharacteristicTaxonomy.REPRODUCTION_RATE))
        .getOrElse(Characteristic(CharacteristicTaxonomy.REPRODUCTION_RATE, defaultExpressionValue)).asInstanceOf[Characteristic with IntExpression]
    override lazy val longevity: Characteristic with IntExpression =
      this.characteristics.find(_.name.equals(CharacteristicTaxonomy.LONGEVITY))
        .getOrElse(Characteristic(CharacteristicTaxonomy.LONGEVITY, defaultExpressionValue)).asInstanceOf[Characteristic with IntExpression]
    override lazy val speed: Characteristic with IntExpression =
      this.characteristics.find(_.name.equals(CharacteristicTaxonomy.SPEED))
        .getOrElse(Characteristic(CharacteristicTaxonomy.SPEED, defaultExpressionValue)).asInstanceOf[Characteristic with IntExpression]
    override lazy val color: Characteristic with DoubleExpression =
      this.characteristics.find(_.name.equals(CharacteristicTaxonomy.COLOR))
        .getOrElse(Characteristic(CharacteristicTaxonomy.COLOR, defaultExpressionValue)).asInstanceOf[Characteristic with DoubleExpression]

  }

}
