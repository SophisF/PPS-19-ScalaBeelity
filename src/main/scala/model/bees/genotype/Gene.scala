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
   * Strategy that binds a gene taxonomy to its genetic information.
   * @return a function to map a gene into his genetic information.
   */
  private val defaultGeneticMapper: GeneTaxonomy => GeneticInformation = {
    case GeneTaxonomy.TEMPERATURE_GENE => GeneticInformation((CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, Influence()))
    case GeneTaxonomy.PRESSURE_GENE => GeneticInformation((CharacteristicTaxonomy.PRESSURE_COMPATIBILITY, Influence()))
    case GeneTaxonomy.HUMIDITY_GENE => GeneticInformation((CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY, Influence()))
    case GeneTaxonomy.AGGRESSION_GENE => GeneticInformation((CharacteristicTaxonomy.AGGRESSION_RATE, Influence()))
    case GeneTaxonomy.REPRODUCTION_GENE => GeneticInformation((CharacteristicTaxonomy.REPRODUCTION_RATE,
      Influence()))
    case GeneTaxonomy.LONGEVITY_GENE => GeneticInformation((CharacteristicTaxonomy.LONGEVITY, Influence()))
    case GeneTaxonomy.GROWTH_GENE => GeneticInformation((CharacteristicTaxonomy.SPEED, Influence(
      typeOfInfluence = InfluenceType.NEGATIVE,
      influenceInPercentage = 10
    )), (CharacteristicTaxonomy.AGGRESSION_RATE, Influence()))
    case _ => GeneticInformation((CharacteristicTaxonomy.SPEED, Influence()))
  }

  /**
   * Strategy that defines which gene has influence on the environmental characteristics.
   * @return a function to check if a gene has influence on environmental characteristics.
   */
  private val defaultEnvironmentalDefiner: GeneticInformation => Boolean = {
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
   * @param taxonomy the gene taxonomy.
   * @param freq the frequency of the gene.
   * @param mapToInformation a strategy to map a taxonomy to a genetic information.
   * @param defineIsEnvironmental a startegy to check if a genetic information map environmental characteristics.
   * @return a new gene.
   */
  def apply(taxonomy: GeneTaxonomy, freq: Frequency = minFrequency + Random.nextInt(maxFrequency-minFrequency),
            mapToInformation: GeneTaxonomy => GeneticInformation = this.defaultGeneticMapper,
            defineIsEnvironmental: GeneticInformation => Boolean = this.defaultEnvironmentalDefiner): Gene =

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

