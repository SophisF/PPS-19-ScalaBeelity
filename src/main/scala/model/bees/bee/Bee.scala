package scala.model.bees.bee

import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.Phenotype.Phenotype

/**
 * Object that represents bee.
 */
object Bee {
  /**
   * Apply method for bee.
   *
   * @param _genotype          the bee's genotype.
   * @param _phenotype         the bee's phenotype.
   * @param age                the age of the bee.
   * @param averageTemperature the temperature of the environment where the bee's colony is.
   * @param averagePressure    the pressure of the environment where the bee's colony is.
   * @param averageHumidity    the humidity of the environment where the bee's colony is.
   * @return a new bee.
   */
  def apply(_genotype: Genotype, _phenotype: Phenotype, age: Int, averageTemperature: Int,
            averagePressure: Int, averageHumidity: Int): Bee = {
    val currentAge: Int = {
      val a: Int = _phenotype.expressionOf(CharacteristicTaxonomy.LONGEVITY)
      if (age > a) a else age
    }
    val fitValue: Double = Fitter
      .calculateFitValue(_phenotype)(averageTemperature)(averagePressure)(averageHumidity)(
        (temperature, pressure, humidity) => (temperature + pressure + humidity) / 3)

    new Bee {
      override val genotype: Genotype = _genotype
      override val phenotype: Phenotype = _phenotype
      override val age: Int = currentAge
      override val effectiveLongevity: Int = {
        val l: Int = _phenotype.expressionOf(CharacteristicTaxonomy.LONGEVITY)
        Fitter.applyFitValue(fitValue)(l - currentAge)(_ * _)
      }
      override val effectiveReproductionRate: Int = Fitter.applyFitValue(fitValue)(_phenotype.expressionOf(CharacteristicTaxonomy.REPRODUCTION_RATE))(_ * _)
      override val effectiveAggression: Int = Fitter.applyFitValue(fitValue)(_phenotype.expressionOf(CharacteristicTaxonomy.AGGRESSION_RATE))(_ * _)

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
     * @param time               the time occurred.
     * @param averageTemperature the average temperature of the environment where the bee's colony is.
     * @param averagePressure    the average pressure of the environment where the bee's colony is.
     * @param averageHumidity    the average humidity of the environment where the bee's colony is.
     * @return a new bee, in the next iteration of the simulation.
     */
    def update(time: Int)(averageTemperature: Int)(averagePressure: Int)(averageHumidity: Int): Bee = {
      Bee(this.genotype, this.phenotype, this.age + time, averageTemperature, averagePressure, averageHumidity)

    }
  }

}
