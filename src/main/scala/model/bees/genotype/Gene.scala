package scala.model.bees.genotype

import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.genotype.GeneticInformation.GeneticInformation
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.util.Random

/**
 * Object that represent a gene.
 */
object Gene {
  /**
   * The type of the frequency, expressed as an integer value.
   */
  type Frequency = Int


  /**
   * Strategy that defines which gene has influence on the environmental characteristics.
   * @return a function to check if a gene has influence on environmental characteristics.
   */
  private val defaultMapper: GeneticInformation => Boolean = {
    geneticInformation => geneticInformation.characteristics.exists(_ match {
      case CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY => true
      case CharacteristicTaxonomy.PRESSURE_COMPATIBILITY => true
      case CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY => true
      case _ => false
    })
  }

  val maxFrequency: Int = 100
  val minFrequency: Int = 1

  /**
   * Apply method for gene.
   * @param _taxonomy the gene taxonomy.
   * @param _frequency the frequency of the gene.
   * @param defineIsEnvironmental a startegy to check if a genetic information map environmental characteristics.
   * @return a new gene.
   */
  def apply(_taxonomy: GeneTaxonomy, _frequency: Frequency = minFrequency + Random.nextInt(maxFrequency-minFrequency),
            defineIsEnvironmental: GeneticInformation => Boolean = this.defaultMapper): Gene =

    new Gene {
      override val taxonomy: GeneTaxonomy = _taxonomy
      override val frequency: Frequency = _frequency match {
        case i if i < minFrequency => minFrequency
        case i if i > maxFrequency => maxFrequency
        case _ => _frequency
      }
      override val geneticInformation: GeneticInformation = GeneticInformation(taxonomy)
      override val isEnvironmental: Boolean = defineIsEnvironmental (geneticInformation)
    }

  /**
   * Trait that represents a gene.
   */
  sealed trait Gene {
    val taxonomy: GeneTaxonomy
    val frequency: Frequency
    val geneticInformation: GeneticInformation
    val isEnvironmental: Boolean
  }
}

