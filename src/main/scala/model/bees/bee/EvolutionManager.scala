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
   * @param time                      the time spent.
   * @param calculateEvolutionaryRate a strategy to calculate the evolutionary rate.
   * @return the evolutionary rate.
   */
  private def evolutionaryRate(time: Int)(calculateEvolutionaryRate: Int => Int): Int = calculateEvolutionaryRate(time)

  /**
   * Method that build a new genotype with a better adaptation to the environment and casual variations.
   *
   * @param genotype           the existing genotype.
   * @param phenotype          the existing phenotype.
   * @param averageTemperature the average temperature of the environment where the bee's colony is.
   * @param averagePressure    the average pressure of the environment where the bee's colony is.
   * @param averageHumidity    the average humidity of the environment where the bee's colony is.
   * @param time               the time spent.
   * @return a new genotype with better adaptation to the environment and casual variations.
   */
  def buildGenotype(genotype: Genotype)(phenotype: Phenotype)(averageTemperature: Int)(averagePressure: Int)(averageHumidity: Int)(time: Int): Genotype = {
    val evolutionaryRate = this.evolutionaryRate(time)(time => Math.sqrt(time).toInt)
    var genes: Set[Gene] = Set.empty
    genes = genes ++ genotype.genes.filter(_.isEnvironmental) map (gene => gene.name match {
      case GeneTaxonomy.TEMPERATURE_GENE => environmentalAdaptation(genotype)(phenotype)(averageTemperature)(gene.name)(gene.geneticInformation.characteristics.head)(evolutionaryRate)
      case GeneTaxonomy.PRESSURE_GENE => environmentalAdaptation(genotype)(phenotype)(averagePressure)(gene.name)(gene.geneticInformation.characteristics.head)(evolutionaryRate)
      case GeneTaxonomy.HUMIDITY_GENE => environmentalAdaptation(genotype)(phenotype)(averageHumidity)(gene.name)(gene.geneticInformation.characteristics.head)(evolutionaryRate)
    })
    genes = genes ++ this.randomMutation(genotype)(evolutionaryRate)

    Genotype(genes)

  }

  /**
   * Generic method that products the environmental adaptation.
   * @param genotype       the existing genotype.
   * @param phenotype      the existing phenotype.
   * @param parameter      the environmental parameter.
   * @param gene           the taxonomy of the gene.
   * @param characteristic the taxonomy of the characteristic expressed by the gene.
   * @return a new gene with a frequency that fit better the environment.
   */
  private def environmentalAdaptation(genotype: Genotype)(phenotype: Phenotype)(parameter: Int)
                                     (gene: GeneTaxonomy)(characteristic: CharacteristicTaxonomy)(evolutionaryRate: Int): Gene = {
    val frequency: Int = genotype frequencyOf gene
    val expression: (Int, Int) = phenotype expressionOf characteristic

    if (parameter in expression) Gene(gene, frequency)
    else if (parameter < expression)
      Gene(gene, frequency - evolutionaryRate)
    else Gene(gene, frequency + evolutionaryRate)
  }

  /**
   * Method to apply random mutation to a genotype.
   * @param genotype the genotype.
   * @param evolutionaryRate the change applied by the mutation.
   * @return a new set of genes, with mutated frequency.
   */
  private def randomMutation(genotype: Genotype)(evolutionaryRate: Int): Set[Gene] = {
    genotype.genes.filterNot(_.isEnvironmental).map(gene => Gene(gene.name, if (Random.nextInt() % 2 == 0)
      gene.frequency - evolutionaryRate else gene.frequency + evolutionaryRate))
  }

  /**
   * Method to calculate the average genotype in a sequence of bees.
   *
   * @param bees the set of bees.
   * @return the average genotype.
   */
  def calculateAverageGenotype(bees: Set[Bee]): Genotype = {
    Genotype(GeneTaxonomy.values.unsorted.map(value => Gene(value, {
      bees.map(_.genotype.genes.toList.filter(_.name.equals(value)).head.frequency).sum / bees.size
    })))
  }

  /**
   * Method to calculate the average phenotype in a sequence of bees.
   *
   * @param bees the set of bees.
   * @return the average phenotype.
   */
  def calculateAveragePhenotype(bees: Set[Bee]): Phenotype = {
    Phenotype(Genotype.calculateExpression(this.calculateAverageGenotype(bees)))
  }

}
