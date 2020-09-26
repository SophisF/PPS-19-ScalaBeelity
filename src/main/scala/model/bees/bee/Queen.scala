package scala.model.bees.bee

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.utility.{EvolutionManager, FitCalculator}
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.utility.PimpTuple._
import scala.utility.Point

/**
 * Object that represents the queen bee.
 */
object Queen {

  /**
   * Apply method for the queen.
   *
   * @param colonyOpt          an optional which contains her colony, if it exists.
   * @param genotype           the genotype.
   * @param phenotype          the phenotype.
   * @param age                the age of the queen.
   * @param averageTemperature the average temperature of the environment where the colony is.
   * @param averagePressure    the average pressure of the environment where the colony is.
   * @param averageHumidity    the average humidity of the environment where the colony is.
   * @param position           the position of the queen.
   * @param generateNewColony  a strategy function to generate a new colony if the queen is just born.
   * @return a new queen.
   */
  def apply(colonyOpt: Option[Colony],
            genotype: Genotype, phenotype: Phenotype,
            age: Int, averageTemperature: Int,
            averagePressure: Int, averageHumidity: Int,
            position: Point, generateNewColony: Point => Colony): Queen = {
    val fitValue: Double = FitCalculator.calculateFitValue(phenotype)(averageTemperature)(averagePressure)(averageHumidity)(
      params => params.sum / params.size)

    val l: Int = phenotype expressionOf CharacteristicTaxonomy.LONGEVITY
    QueenImpl(colonyOpt, genotype, phenotype, age, FitCalculator.applyFitValue(fitValue)(l - age)(_ * _),
      FitCalculator.applyFitValue(fitValue)(phenotype expressionOf CharacteristicTaxonomy.REPRODUCTION_RATE)(_ * _),
      FitCalculator.applyFitValue(fitValue)(phenotype expressionOf CharacteristicTaxonomy.AGGRESSION_RATE)(_ * _),
      averageTemperature, averagePressure, averageHumidity, position, generateNewColony)
  }

  /**
   * Trait for a queen
   */
  trait Queen extends Bee {
    val colony: Colony
    val position: Point

    /**
     * Strategy method to generate new colony.
     */
    val generateNewColony: Point => Colony

    /**
     * Method that map a queen into a new queen in the next iteration of the simulation.
     *
     * @param time               the time that has passed in the simulation.
     * @param averageTemperature the average temperature of the environment where the colony is.
     * @param averagePressure    the average pressure of the environment where the colony is.
     * @param averageHumidity    the average humidity of the environment where the colony is.
     * @param position           the new position of the queen.
     * @return a new queen updated.
     */
    def update(time: Int)(averageTemperature: Int)(averagePressure: Int)(averageHumidity: Int)(position: Point): Queen =
      Queen(Some(this.colony), this.genotype, this.phenotype, this.age + time, averageTemperature, averagePressure, averageHumidity,
        position, this.generateNewColony)
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
          similarGenotype expressInPhenotype,
          0,
          averageTemperature, averagePressure, averageHumidity
        )
      }).toSet
  }

}