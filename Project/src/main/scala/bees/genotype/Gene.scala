package bees.genotype

import bees.genotype.GeneTaxonomy.GeneTaxonomy
import bees.genotype.GeneticInformation.{GeneticInformation, GeneticInformationImpl}
import bees.genotype.Influence.InfluenceImpl
import bees.phenotype.CharacteristicTaxonomy

import scala.util.Random

object Gene {

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

  def apply(taxonomy: Option[GeneTaxonomy] = None): Gene = {
    taxonomy match {
      case Some(t) => geneFactory(t)
      case None => geneFactory(GeneTaxonomy(Random.nextInt(GeneTaxonomy.values.size)))
    }
  }

  trait Gene {
    val name: GeneTaxonomy
  }

  trait CodingInformation {
    val information: GeneticInformation
  }

  private case class TemperatureCompatibilityGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.TEMPERATURE_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, InfluenceImpl()))

  }

  private case class PressureCompatibilityGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.PRESSURE_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.PRESSURE_COMPATIBILITY, InfluenceImpl()))
  }

  private case class HumidityCompatibilityGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.HUMIDITY_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY, InfluenceImpl()))
  }

  private case class AggressionGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.AGGRESSION_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.AGGRESSION_RATE, InfluenceImpl()))
  }

  private case class ReproductionRateGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.REPRODUCTION_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.REPRODUCTION_RATE,
      InfluenceImpl()))
  }

  private case class LongevityGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.LONGEVITY_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.LONGEVITY, InfluenceImpl()))
  }

  private case class ColorGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.COLOR_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.COLOR, InfluenceImpl()))
  }

  private case class GrowthGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.GROWTH_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.SPEED, InfluenceImpl(
      typeOfInfluence = InfluenceType.NEGATIVE,
      influenceInPercentage = 20
    )), (CharacteristicTaxonomy.AGGRESSION_RATE, InfluenceImpl()))
  }

  private case class WingsGene() extends Gene with CodingInformation {
    override val name: GeneTaxonomy = GeneTaxonomy.WINGS_GENE
    override val information: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.SPEED, InfluenceImpl()))
  }

  private case class NonCodingGene() extends Gene{
    override val name: GeneTaxonomy = GeneTaxonomy.NON_CODING
  }

}
