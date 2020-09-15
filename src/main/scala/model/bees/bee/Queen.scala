package scala.model.bees.bee

import model.bees.bee.EvolutionManager

import scala.model.Ecosystem
import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Colony.{Colony, ColonyImpl}
import scala.model.bees.genotype.Genotype
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.Phenotype
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.environment.matrix.Point
import scala.util.Random
import scala.model.bees.utility.PimpTuple._

/**
 * Object that represents the queen bee
 */
object Queen {

  def apply(colonyOpt: Option[Colony],
            genotype: Genotype, phenotype: Phenotype,
            age: Int, temperature: Int,
            pressure: Int, humidity: Int,
            position: Point,
            generateNewColony: Point => Colony): Queen = {
    val fitValue: Double = Fitter.calculateFitValue(phenotype)(temperature)(pressure)(humidity)

    QueenImpl(colonyOpt, genotype, phenotype, age, Fitter.applyFitValue(fitValue)(phenotype.longevity.expression - age)(_ * _),
      Fitter.applyFitValue(fitValue)(phenotype.reproductionRate.expression)(_ * _),
      Fitter.applyFitValue(fitValue)(phenotype.aggression.expression)(_ * _),
      temperature, pressure, humidity, position, generateNewColony)
  }

  /**
   * Trait for a queen
   */
  trait Queen extends Bee {
    val colony: Colony
    val position: Point
    val canGenerate: Boolean
    val generateNewColony: Point => Colony

  }

  private case class QueenImpl(colonyOpt: Option[Colony],
                               override val genotype: Genotype, override val phenotype: Phenotype,
                               override val age: Int, override val effectiveLongevity: Int, override val effectiveReproductionRate: Int,
                               override val effectiveAggression: Int, private val temperature: Int, private val pressure: Int,
                               private val humidity: Int, override val position: Point, override val generateNewColony: Point => Colony) extends Queen {

    override val colony: Colony = colonyOpt getOrElse ColonyImpl(this, generateBee)

    private def generateBee: Seq[Bee] = List.range(0, this.effectiveReproductionRate + 1)
      .map(_ => {
        val similarGenotype = EvolutionManager.buildGenotype(this.genotype)(this.phenotype)(this.phenotype.temperatureCompatibility
          .expression.average)(this.phenotype.pressureCompatibility.expression.average)(this.phenotype.humidityCompatibility
          .expression.average)(1)
        Bee(
          similarGenotype,
          Phenotype(Genotype.calculateExpression(similarGenotype)),
          0,
          temperature, pressure, humidity
        )
      })

    override val canGenerate: Boolean = this.colony.numberOfBees < this.colony.maxBees
  }

}