package scala.model.bees.bee

import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.Phenotype.Phenotype

/**
 * Object that represents bee
 */
object Bee {

  def apply(genotype: Genotype, phenotype: Phenotype, age: Int, temperature: Int, pressure: Int, humidity: Int,
            canGenerate: Int = 0, lastAgeFromBrood: Int = 0): Bee = {
    val currentAge = if(age > phenotype.longevity.expression) phenotype.longevity.expression else age
    val fitValue: Double = Fitter.calculateFitValue(phenotype)(temperature)(pressure)(humidity)
    BeeImpl(
      genotype, phenotype, currentAge, Fitter.applyFitValue(fitValue)(phenotype.longevity.expression - currentAge)(_ * _),
      Fitter.applyFitValue(fitValue)(phenotype.reproductionRate.expression)(_ * _),
      Fitter.applyFitValue(fitValue)(phenotype.aggression.expression)(_ * _))
  }

  /**
   * Trait that represents bee
   */
  trait Bee {
    val genotype: Genotype
    val phenotype: Phenotype
    val age: Int
    val effectiveLongevity: Int
    val effectiveReproductionRate: Int
    val effectiveAggression: Int
    def update(time: Int, temperature: Int, pressure: Int, humidity: Int): Bee = {

      Bee(this.genotype, this.phenotype, this.age + time, temperature, pressure, humidity)

    }

    val isAlive: Boolean = this.effectiveLongevity > 0
  }

  /**
   * Class that represents bee
   *
   * @param genotype  genotype of the bee
   * @param phenotype phenotype of the bee
   */
  case class BeeImpl(override val genotype: Genotype, override val phenotype: Phenotype,
                     override val age: Int, override val effectiveLongevity: Int,
                     override val effectiveReproductionRate: Int, override val effectiveAggression: Int) extends Bee {
  }

}
