package scala.model.bees.bee.utility

import scala.model.bees.genotype.Gene.{Frequency, Gene}
import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.genotype.{Gene, GeneTaxonomy, Genotype}
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.EnvironmentInformation.EnvironmentInformation
import scala.util.Random
import scala.utility.PimpInt._

/**
 * Singleton used to manage the evolution of the bees.
 */
private[bee] object EvolutionManager {

  /**
   * Method that build a new genotype with a better adaptation to the environment and casual variations.
   *
   * @param genotype               the existing genotype.
   * @param environmentInformation the information of the environment.
   * @param time                   the time spent.
   * @return a new genotype with better adaptation to the environment and casual variations.
   */
  def evolveGenotype(genotype: Genotype)(environmentInformation: EnvironmentInformation)(time: Int): Genotype = {
    val evolutionaryRate = this.evolutionaryRate(time)(time => Math.sqrt(time).toInt)

    val phenotype = genotype expressInPhenotype

    /**
     * Inner function to create a new genotype, evolved with respect to the given genotype.
     *
     * @param genes    a list of current genes of the given genotype.
     * @param newGenes a list of new genes.
     * @return the list of new genes.
     */
    @scala.annotation.tailrec
    def createGenotype(genes: List[Gene], newGenes: List[Gene] = List.empty): List[Gene] = genes match {
      case h :: t => val gene = h taxonomy match {
        case GeneTaxonomy.TEMPERATURE_GENE => environmentalAdaptation(h taxonomy)(h frequency)(
          phenotype expressionOf CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY)(
          environmentInformation.characteristicMap(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY))(evolutionaryRate)
        case GeneTaxonomy.PRESSURE_GENE => environmentalAdaptation(h taxonomy)(h frequency)(
          phenotype expressionOf CharacteristicTaxonomy.PRESSURE_COMPATIBILITY)(
          environmentInformation.characteristicMap(CharacteristicTaxonomy.PRESSURE_COMPATIBILITY))(evolutionaryRate)
        case GeneTaxonomy.HUMIDITY_GENE => environmentalAdaptation(h taxonomy)(h frequency)(
          phenotype expressionOf CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY)(
          environmentInformation.characteristicMap(CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY))(evolutionaryRate)
        case _ => this.randomMutation(h)(evolutionaryRate)
      }
        createGenotype(t, gene :: newGenes)
      case _ => newGenes
    }

    Genotype(createGenotype(genotype.genes.toList): _*)
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
   * @param taxonomy         the taxonomy of the gene.
   * @param frequency        the frequency of the gene in the genotype.
   * @param expression       the expression of the characteristic.
   * @param parameter        the environmental parameter.
   * @param evolutionaryRate the evolutionary rate of the gene.
   * @return a new gene with a frequency that fit better the environment.
   */
  private def environmentalAdaptation(taxonomy: GeneTaxonomy)(frequency: Frequency)(expression: (Int, Int))
                                     (parameter: Int)(evolutionaryRate: Int): Gene = {

    if (parameter in expression) Gene(taxonomy, frequency)
    else if (parameter < expression)
      Gene(taxonomy, frequency - evolutionaryRate)
    else Gene(taxonomy, frequency + evolutionaryRate)
  }

  /**
   * Method to apply random mutation to a gene.
   *
   * @param gene             the gene.
   * @param evolutionaryRate the change applied by the mutation.
   * @return a new gene, with mutated frequency.
   */
  private def randomMutation(gene: Gene)(evolutionaryRate: Int): Gene = {
    Gene(gene taxonomy, if (Random.nextInt() % 2 == 0) (gene frequency) - evolutionaryRate else (gene frequency) + evolutionaryRate)
  }
}
