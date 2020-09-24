package scala.model.bees.bee

import model.bees.bee.EvolutionManager

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Colony.Colony
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.utility.PimpTuple._
import scala.utility.Point

/**
 * Object that represents the queen bee
 */
object Queen {

  def apply(colonyOpt: Option[Colony],
            genotype: Genotype, phenotype: Phenotype,
            age: Int, averageTemperature: Int,
            averagePressure: Int, averageHumidity: Int,
            position: Point, generateNewColony: Point => Colony): Queen = {
    val fitValue: Double = Fitter.calculateFitValue(phenotype)(averageTemperature)(averagePressure)(averageHumidity)(
      (temperature, pressure, humidity) => (temperature + pressure + humidity) / 3)

    val l: Int = phenotype.expressionOf(CharacteristicTaxonomy.LONGEVITY)
    QueenImpl(colonyOpt, genotype, phenotype, age, Fitter.applyFitValue(fitValue)(l - age)(_ * _),
      Fitter.applyFitValue(fitValue)(phenotype.expressionOf(CharacteristicTaxonomy.REPRODUCTION_RATE))(_ * _),
      Fitter.applyFitValue(fitValue)(phenotype.expressionOf(CharacteristicTaxonomy.AGGRESSION_RATE))(_ * _),
      averageTemperature, averagePressure, averageHumidity, position, generateNewColony)
  }

  /**
   * Trait for a queen
   */
  trait Queen extends Bee {
    val colony: Colony
    val position: Point
    val generateNewColony: Point => Colony

    def update(time: Int)(averageTemperature: Int)(averagePressure: Int)(averageHumidity: Int)(newCenter: Point): Queen =
      Queen(Some(this.colony), this.genotype, this.phenotype, this.age + time, averageTemperature, averagePressure, averageHumidity,
        newCenter, this.generateNewColony)
  }

  private case class QueenImpl(colonyOpt: Option[Colony],
                               override val genotype: Genotype, override val phenotype: Phenotype,
                               override val age: Int, override val effectiveLongevity: Int, override val effectiveReproductionRate: Int,
                               override val effectiveAggression: Int, private val averageTemperature: Int,
                               private val averagePressure: Int, private val averageHumidity: Int,
                               override val position: Point, override val generateNewColony: Point => Colony) extends Queen {

    override val colony: Colony = colonyOpt getOrElse Colony(queen = this, bees = generateBee)

    private def generateBee: Set[Bee] = (0 to this.effectiveReproductionRate + 1)
      .map(_ => {
        val t: (Int, Int) = this.phenotype.expressionOf(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY)
        val p: (Int, Int) = this.phenotype.expressionOf(CharacteristicTaxonomy.PRESSURE_COMPATIBILITY)
        val h: (Int, Int) = this.phenotype.expressionOf(CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY)
        val similarGenotype = EvolutionManager.buildGenotype(this.genotype)(this.phenotype)(t.average)(p.average)(h.average)(1)
        Bee(
          similarGenotype,
          similarGenotype expressItself,
          0,
          averageTemperature, averagePressure, averageHumidity
        )
      }).toSet
  }

}