package scala.model.bees.bee

import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.Phenotype.Phenotype
import model.bees.bee.Fitter

/**
 * Object that represents bee
 */
object Bee {

  def fromBee(bee: Bee, age: Int, temperature: Int, pressure: Int, humidity: Int): Bee = BeeImpl(bee.genotype, bee.phenotype, age, temperature, pressure, humidity)

  def calculateRemainingLife(bee: Bee, temperature: Int, pressure: Int, humidity: Int): Int = {
    val tFit: Double = Fitter.calculateFit(temperature)(bee.phenotype.temperatureCompatibility.expression)
    val pFit: Double = Fitter.calculateFit(pressure)(bee.phenotype.pressureCompatibility.expression)
    val hFit: Double = Fitter.calculateFit(humidity)(bee.phenotype.humidityCompatibility.expression)

    ((bee.phenotype.longevity.expression - bee.age).toDouble * ((tFit + pFit + hFit) / 3)).toInt
  }
  /**
   * Trait that represents bee
   */
  trait Bee {
    val genotype: Genotype
    val phenotype: Phenotype
    val age: Int
    val remainingDaysOfLife: Int

    def isAlive: Boolean = this.remainingDaysOfLife > 0
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

    override val remainingDaysOfLife: Int = calculateRemainingLife(this, temperature, pressure, humidity)


  }

}
