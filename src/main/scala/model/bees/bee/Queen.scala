package scala.model.bees.bee

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.utility.{EvolutionManager, FitCalculator}
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.EnvironmentInformation.EnvironmentInformation
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.utility.Point

/**
 * Object that represents the queen bee.
 */
object Queen {

  /**
   * Apply method for the queen.
   *
   * @param colonyOpt              an optional which contains her colony, if it exists.
   * @param genotype               the genotype.
   * @param phenotype              the phenotype.
   * @param age                    the age of the queen.
   * @param environmentInformation the information of the environment.
   * @param position               the position of the queen.
   * @param generateNewColony      a strategy function to generate a new colony if the queen is just born.
   * @return a new queen.
   */
  def apply(colonyOpt: Option[Colony],
            genotype: Genotype, phenotype: Phenotype, age: Int, position: Point,
            generateNewColony: Point => Colony, environmentInformation: EnvironmentInformation): Queen = {
    val fitValue: Double = FitCalculator.calculateFitValue(phenotype)(environmentInformation)(
      params => params.sum / params.size)

    val l: Int = phenotype expressionOf CharacteristicTaxonomy.LONGEVITY
    QueenImpl(colonyOpt, genotype, phenotype, age, FitCalculator.applyFitValue(fitValue)(l - age)(_ * _),
      FitCalculator.applyFitValue(fitValue)(phenotype expressionOf CharacteristicTaxonomy.REPRODUCTION_RATE)(_ * _),
      FitCalculator.applyFitValue(fitValue)(phenotype expressionOf CharacteristicTaxonomy.AGGRESSION_RATE)(_ * _),
      environmentInformation, position, generateNewColony)
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
     * @param time                   the time that has passed in the simulation.
     * @param environmentInformation the information of the environment.
     * @param position               the new position of the queen.
     * @return a new queen updated.
     */
    def update(time: Int)(environmentInformation: EnvironmentInformation)(position: Point): Queen =
      Queen(Some(this.colony), this.genotype, this.phenotype, this.age + time, position,
        this.generateNewColony, environmentInformation)
  }

  private case class QueenImpl(colonyOpt: Option[Colony],
                               override val genotype: Genotype, override val phenotype: Phenotype,
                               override val age: Int, override val effectiveLongevity: Int, override val effectiveReproductionRate: Int,
                               override val effectiveAggression: Int, private val environmentInformation: EnvironmentInformation,
                               override val position: Point, override val generateNewColony: Point => Colony) extends Queen {

    override val colony: Colony = colonyOpt getOrElse Colony(queen = this, bees = generateBee)

    private def generateBee: Set[Bee] = (0 to this.effectiveReproductionRate + 1)
      .map(_ => {
        val similarGenotype = EvolutionManager.evolveGenotype(this.genotype)(environmentInformation)(1)
        Bee(
          similarGenotype,
          similarGenotype expressInPhenotype,
          0,
          environmentInformation
        )
      }) toSet
  }

}