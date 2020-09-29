package scala.model.bees.bee

import scala.model.bees.bee.utility.FitCalculator
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.EnvironmentInformation.EnvironmentInformation
import scala.model.bees.phenotype.Phenotype.Phenotype

/**
 * Object that represents bee.
 */
object Bee {
  /**
   * Apply method for bee.
   *
   * @param _genotype              the bee's genotype.
   * @param _phenotype             the bee's phenotype.
   * @param age                    the age of the bee.
   * @param environmentInformation the information of the environment.
   * @return a new bee.
   */
  def apply(_genotype: Genotype, _phenotype: Phenotype, age: Int, environmentInformation: EnvironmentInformation): Bee = {
    /**
     * Variable that represents the current age of the bee, if its greater than the longevity it lives for another iteration
     */
    val currentAge: Int = {
      val a: Int = _phenotype.expressionOf(CharacteristicTaxonomy.LONGEVITY)
      if (age > a) a else age
    }

    /**
     * Variable that represents the fit value that represents how good it is in that position
     */
    val fitValue: Double =
      FitCalculator.calculateFitValue(_phenotype)(environmentInformation)(params => params.sum / params.size)

    new Bee {
      override val genotype: Genotype = _genotype
      override val phenotype: Phenotype = _phenotype
      override val age: Int = currentAge
      override val effectiveLongevity: Int = {
        val l: Int = _phenotype expressionOf CharacteristicTaxonomy.LONGEVITY
        FitCalculator.applyFitValue(fitValue)(l - currentAge)(_ * _)
      }
      override val effectiveReproductionRate: Int =
        FitCalculator.applyFitValue(fitValue)(_phenotype expressionOf CharacteristicTaxonomy.REPRODUCTION_RATE)(_ * _)
      override val effectiveAggression: Int =
        FitCalculator.applyFitValue(fitValue)(_phenotype expressionOf CharacteristicTaxonomy.AGGRESSION_RATE)(_ * _)
    }
  }

  /**
   * Trait that represents bee.
   */
  trait Bee {
    val genotype: Genotype
    val phenotype: Phenotype
    val age: Int
    val effectiveLongevity: Int
    val effectiveReproductionRate: Int
    val effectiveAggression: Int

    /**
     * Method to check if a bee is alive.
     *
     * @return a boolean, true if the bee is alive, false otherwise.
     */
    def isAlive: Boolean = this.effectiveLongevity > 0

    /**
     * Method that map a bee to a new bee in a successive iteration of the simulation.
     *
     * @param time                   the time occurred.
     * @param environmentInformation the information of the environment.
     * @return a new bee, in the next iteration of the simulation.
     */
    def update(time: Int)(environmentInformation: EnvironmentInformation): Bee =
      Bee(this.genotype, this.phenotype, this.age + time, environmentInformation)
  }

}
