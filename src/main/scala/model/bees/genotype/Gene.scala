package scala.model.bees.genotype

import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.genotype.GeneticInformation.GeneticInformation
import scala.util.Random

/**
 * Object that represent a gene.
 */
object Gene {
  /**
   * The type of the frequency, expressed as an integer value.
   */
  type Frequency = Int

  val maxFrequency: Int = 100
  val minFrequency: Int = 1

  /**
   * Apply method for gene.
   * @param taxonomy the gene taxonomy.
   * @param freq the frequency of the gene.
   * @param mapToInformation a strategy to map a taxonomy to a genetic information.
   * @param defineIsEnvironmental a startegy to check if a genetic information map environmental characteristics.
   * @return a new gene.
   */
  def apply(taxonomy: GeneTaxonomy, freq: Frequency = minFrequency + Random.nextInt(maxFrequency-minFrequency),
            mapToInformation: GeneTaxonomy => GeneticInformation = taxonomy => GeneManager geneticMapper taxonomy,
            defineIsEnvironmental: GeneticInformation => Boolean = information => GeneManager environmentalDefiner information): Gene =

    new Gene {
      override val name: GeneTaxonomy = taxonomy
      override val frequency: Frequency = freq match {
        case i if i < minFrequency => minFrequency
        case i if i > maxFrequency => maxFrequency
        case _ => freq
      }
      override val geneticInformation: GeneticInformation = mapToInformation (name)
      override val isEnvironmental: Boolean = defineIsEnvironmental (geneticInformation)
    }

  /**
   * Trait that represents a gene.
   */
  sealed trait Gene {
    val name: GeneTaxonomy
    val frequency: Frequency
    val geneticInformation: GeneticInformation
    val isEnvironmental: Boolean
  }
}

