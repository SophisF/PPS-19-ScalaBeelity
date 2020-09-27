package scala.model.bees.bee.utility

import scala.model.bees.genotype.Gene.Gene
import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.genotype.{Gene, GeneTaxonomy, Genotype}
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.util.Random
import scala.utility.PimpInt._

object EvolutionManager {

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

    /**
     * Inner function to create a new genotype, evolved with respect to the given genotype.
     *
     * @param genes    a list of current genes of the given genotype.
     * @param newGenes a list of new genes.
     * @return the list of new genes.
     */
    @scala.annotation.tailrec
    def createGenotype(genes: List[Gene], newGenes: List[Gene] = List.empty): List[Gene] = genes match {
      case h :: t => val gene = h.name match {
        case GeneTaxonomy.TEMPERATURE_GENE => environmentalAdaptation(genotype)(phenotype)(averageTemperature)(h.name)(h.geneticInformation.characteristics.head)(evolutionaryRate)
        case GeneTaxonomy.PRESSURE_GENE => environmentalAdaptation(genotype)(phenotype)(averagePressure)(h.name)(h.geneticInformation.characteristics.head)(evolutionaryRate)
        case GeneTaxonomy.HUMIDITY_GENE => environmentalAdaptation(genotype)(phenotype)(averageHumidity)(h.name)(h.geneticInformation.characteristics.head)(evolutionaryRate)
        case _ => this.randomMutation(h)(evolutionaryRate)
      }
        createGenotype(t, gene :: newGenes)
      case _ => newGenes
    }

    Genotype(createGenotype(genotype.genes.toList) :_*)
  }

  /**
   * Private method to calculate the evolutionary rate based on time spent.
   *
   * @param time                      the time spent.
   * @param calculateEvolutionaryRate a strategy to calculate the evolutionary rate.
   * @return the evolutionary rate.
   */
  private def evolutionaryRate(time: Int)(calculateEvolutionaryRate: Int => Int): Int = calculateEvolutionaryRate(time)

  /**
   * Generic method that products the environmental adaptation.
   *
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
   * Method to apply random mutation to a gene.
   *
   * @param gene             the gene.
   * @param evolutionaryRate the change applied by the mutation.
   * @return a new gene, with mutated frequency.
   */
  private def randomMutation(gene: Gene)(evolutionaryRate: Int): Gene = {
    Gene(gene.name, if (Random.nextInt() % 2 == 0) gene.frequency - evolutionaryRate else gene.frequency + evolutionaryRate)
  }
}
