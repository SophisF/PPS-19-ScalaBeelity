package scala.model.bees.bee

import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.Phenotype.Phenotype

/**
 * Object that represents bee
 */
object Bee {

  def apply(genotype: Genotype, phenotype: Phenotype, age: Int, temperature: Int, pressure: Int, humidity: Int): Bee =
    BeeImpl(genotype, phenotype, age, temperature, pressure, humidity)

  def calculateRemainingLife(bee: Bee, temperature: Int, pressure: Int, humidity: Int): Int = {
    Fitter.calculateFitValue(bee.phenotype)(temperature)(pressure)(humidity).toInt
  }
  /**
   * Trait that represents bee
   */
  trait Bee {
    val genotype: Genotype
    val phenotype: Phenotype
    val age: Int
    val remainingDaysOfLife: Int

    val isAlive: Boolean = this.remainingDaysOfLife > 0
  }

  /**
   * Class that represents bee
   *
   * @param genotype  genotype of the bee
   * @param phenotype phenotype of the bee
   */
  case class BeeImpl(override val genotype: Genotype, override val phenotype: Phenotype,
                     override val age: Int, temperature: Int,
                     pressure: Int, humidity: Int) extends Bee {

    override lazy val remainingDaysOfLife: Int = calculateRemainingLife(this, temperature, pressure, humidity)


  }

}
