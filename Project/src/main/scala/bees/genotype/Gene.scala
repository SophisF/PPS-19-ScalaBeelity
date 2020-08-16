package bees.genotype

import bees.genotype.GeneTaxonomy.GeneTaxonomy
import bees.genotype.GeneticInformation.{GeneticInformation, GeneticInformationImpl}
import bees.genotype.Influence.InfluenceImpl
import bees.phenotype.CharacteristicTaxonomy

import scala.util.Random



/**
  Object that represent a gene.
 */
object Gene {

  implicit def convertToCoding(gene: Gene): Option[Gene with CodingInformation] = gene match {
    case _: Gene with CodingInformation => Some(gene.asInstanceOf[Gene with CodingInformation])
    case _ => None
  }

  /**
   * Private method which acts as a factory for the genes
   * @param taxonomy the gene taxonomy, that represent the name of the gene
   * @return a new gene
   */
  private def geneFactory(taxonomy: GeneTaxonomy): Gene = {
    taxonomy match {
      case GeneTaxonomy.TEMPERATURE_GENE => TemperatureCompatibilityGene()
      case GeneTaxonomy.PRESSURE_GENE => PressureCompatibilityGene()
      case GeneTaxonomy.HUMIDITY_GENE => HumidityCompatibilityGene()
      case GeneTaxonomy.AGGRESSION_GENE => AggressionGene()
      case GeneTaxonomy.REPRODUCTION_GENE => ReproductionRateGene()
      case GeneTaxonomy.LONGEVITY_GENE => LongevityGene()
      case GeneTaxonomy.COLOR_GENE => ColorGene()
      case GeneTaxonomy.GROWTH_GENE => GrowthGene()
      case GeneTaxonomy.WINGS_GENE => WingsGene()
      case _ => NonCodingGene()
    }
  }

  /**
   * Create a new gene
   * @param taxonomy an optional that can wrap a gene taxonomy
   * @return a new gene
   */
  def apply(taxonomy: Option[GeneTaxonomy] = None): Gene = {
    taxonomy match {
      case Some(t) => geneFactory(t)
      case None => geneFactory(GeneTaxonomy(Random.nextInt(GeneTaxonomy.values.size)))
    }
  }

  /**
   * Trait for a gene
   */
  trait Gene {
    val name: GeneTaxonomy
  }

  /**
   * Trait that represent a the coding information
   */
  trait CodingInformation {
    val information: GeneticInformation
  }

  /**
   * Class that represents the temperature compatibility gene.
   */
  private case class TemperatureCompatibilityGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.TEMPERATURE_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, InfluenceImpl()))

  }

  /**
   * Class that represents the pressure compatibility gene.
   */
  private case class PressureCompatibilityGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.PRESSURE_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.PRESSURE_COMPATIBILITY, InfluenceImpl()))
  }

  /**
   * Class that represents the humidity compatibility gene.
   */
  private case class HumidityCompatibilityGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.HUMIDITY_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY, InfluenceImpl()))
  }

  /**
   * Class that represents the aggression gene.
   */
  private case class AggressionGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.AGGRESSION_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.AGGRESSION_RATE, InfluenceImpl()))
  }

  /**
   * Class that represents the reproduction rate gene.
   */
  private case class ReproductionRateGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.REPRODUCTION_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.REPRODUCTION_RATE,
      InfluenceImpl()))
  }

  /**
   * Class that represents the longevity gene.
   */
  private case class LongevityGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.LONGEVITY_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.LONGEVITY, InfluenceImpl()))
  }

  /**
   * Class that represents the color gene.
   */
  private case class ColorGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.COLOR_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.COLOR, InfluenceImpl()))
  }

  /**
   * Class that represents the growth gene.
   */
  private case class GrowthGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.GROWTH_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.SPEED, InfluenceImpl(
      typeOfInfluence = InfluenceType.NEGATIVE,
      influenceInPercentage = 20
    )), (CharacteristicTaxonomy.AGGRESSION_RATE, InfluenceImpl()))
  }

  /**
   * Class that represents the wings growth gene.
   */
  private case class WingsGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.WINGS_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.SPEED, InfluenceImpl()))
  }

  /**
   * Class that represents a non coding gene. This kind of gene hasn't got any kind of coding information
   */
  private case class NonCodingGene() extends Gene{
    override val name: GeneTaxonomy = GeneTaxonomy.NON_CODING
  }

}
