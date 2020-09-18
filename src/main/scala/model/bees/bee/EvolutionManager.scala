package model.bees.bee

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.genotype.Gene.Gene
import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.genotype.{Gene, GeneTaxonomy, Genotype}
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.bees.phenotype.Phenotype
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.bees.utility.PimpInt._
import scala.util.Random

object EvolutionManager {

  /**
   * Private method to calculate the evolutionary rate based on time spent.
   *
   * @param time the time spent.
   * @param calculateEvolutionaryRate a strategy to calculate the evolutionary rate.
   * @return the evolutionary rate.
   */
  private def evolutionaryRate(time: Int)(calculateEvolutionaryRate: Int => Int): Int = calculateEvolutionaryRate (time)

  /**
   * Method that build a new genotype with a better adaptation to the environment and casual variations.
   *
   * @param genotype    the existing genotype.
   * @param phenotype   the existing phenotype.
   * @param temperature the temperature parameter of the environment.
   * @param pressure    the pressure parameter of the environment.
   * @param humidity    the humidity parameter of the environment.
   * @param time        the time spent.
   * @return a new genotype with better adaptation to the environment and casual variations.
   */
  def buildGenotype(genotype: Genotype)(phenotype: Phenotype)(temperature: Int)(pressure: Int)(humidity: Int)(time: Int): Genotype = {

    val evolutionaryRate = this.evolutionaryRate(time)(time => 2* Math.sqrt(time).toInt)

    var genes: Set[Gene] = Set.empty
    genes = genes ++ genotype.genes.filter(_.isEnvironmental).map(gene => gene.name match {
      case GeneTaxonomy.TEMPERATURE_GENE => environmentalAdaptation(genotype)(phenotype)(temperature)(gene.name)(gene.geneticInformation.characteristics.head)(evolutionaryRate)
      case GeneTaxonomy.PRESSURE_GENE => environmentalAdaptation(genotype)(phenotype)(pressure)(gene.name)(gene.geneticInformation.characteristics.head)(evolutionaryRate)
      case GeneTaxonomy.HUMIDITY_GENE => environmentalAdaptation(genotype)(phenotype)(humidity)(gene.name)(gene.geneticInformation.characteristics.head)(evolutionaryRate)
    })
    genes = genes ++ genotype.genes.filterNot(_.isEnvironmental).map(gene => Gene(gene.name, if (Random.nextInt() % 2 == 0)
      gene.frequency - evolutionaryRate else gene.frequency + evolutionaryRate))

    Genotype(genes)

  }

  /**
   * Generic method that products the environmental adaptation.
   *
   * @param genotype               the existing genotype.
   * @param phenotype              the existing phenotype.
   * @param parameter              the environmental parameter.
   * @param geneTaxonomy           the taxonomy of the gene.
   * @param characteristicTaxonomy the taxonomy of the characteristic expressed by the gene.
   * @return a new gene with a frequency that fit better the environment.
   */
  private def environmentalAdaptation(genotype: Genotype)(phenotype: Phenotype)(parameter: Int)
                                     (geneTaxonomy: GeneTaxonomy)(characteristicTaxonomy: CharacteristicTaxonomy)(evolutionaryRate: Int): Gene = {
    val frequency: Int = genotype.frequency(geneTaxonomy)
    val expression: (Int, Int) = phenotype.expressionOf(characteristicTaxonomy)

    if (parameter in expression) Gene(geneTaxonomy, frequency)
    else if (parameter < expression)
      Gene(geneTaxonomy, frequency - evolutionaryRate)
    else Gene(geneTaxonomy, frequency + evolutionaryRate)


  }

  /**
   * Method to calculate the average genotype in a sequence of bees.
   *
   * @param bees the sequence of bees.
   * @return the average genotype.
   */
  def calculateAverageGenotype(bees: Seq[Bee]): Genotype = {
    Genotype(GeneTaxonomy.values.unsorted.map(value => Gene(value, {
      bees.map(_.genotype.genes.toList.filter(_.name.equals(value)).head.frequency).sum / bees.size
    })))
  }

  /**
   * Method to calculate the average phenotype in a sequence of bees.
   *
   * @param bees the sequence of bees.
   * @return the average phenotype.
   */
  def calculateAveragePhenotype(bees: Seq[Bee]): Phenotype = {
    Phenotype(Genotype.calculateExpression(this.calculateAverageGenotype(bees)))
  }

}
