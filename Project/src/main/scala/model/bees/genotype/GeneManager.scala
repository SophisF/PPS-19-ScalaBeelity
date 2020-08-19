package model.bees.genotype

import model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import model.bees.genotype.GeneticInformation.{GeneticInformation, GeneticInformationImpl}
import model.bees.genotype.Influence.InfluenceImpl
import model.bees.phenotype.CharacteristicTaxonomy

/**
 * Singleton to manipulate the genotype in order to create a phenotype.
 */
object GeneManager {

  implicit def geneticMapper(geneTaxonomy: GeneTaxonomy): GeneticInformation = {
    geneTaxonomy match {
      case GeneTaxonomy.TEMPERATURE_GENE => GeneticInformationImpl((CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, InfluenceImpl()))
      case GeneTaxonomy.PRESSURE_GENE => GeneticInformationImpl((CharacteristicTaxonomy.PRESSURE_COMPATIBILITY, InfluenceImpl()))
      case GeneTaxonomy.HUMIDITY_GENE => GeneticInformationImpl((CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY, InfluenceImpl()))
      case GeneTaxonomy.AGGRESSION_GENE => GeneticInformationImpl((CharacteristicTaxonomy.AGGRESSION_RATE, InfluenceImpl()))
      case GeneTaxonomy.REPRODUCTION_GENE => GeneticInformationImpl((CharacteristicTaxonomy.REPRODUCTION_RATE,
        InfluenceImpl()))
      case GeneTaxonomy.LONGEVITY_GENE => GeneticInformationImpl((CharacteristicTaxonomy.LONGEVITY, InfluenceImpl()))
      case GeneTaxonomy.COLOR_GENE => GeneticInformationImpl((CharacteristicTaxonomy.COLOR, InfluenceImpl()))
      case GeneTaxonomy.GROWTH_GENE => GeneticInformationImpl((CharacteristicTaxonomy.SPEED, InfluenceImpl(
        typeOfInfluence = InfluenceType.NEGATIVE,
        influenceInPercentage = 10
      )), (CharacteristicTaxonomy.AGGRESSION_RATE, InfluenceImpl()))
      case _ => GeneticInformationImpl((CharacteristicTaxonomy.SPEED, InfluenceImpl()))
    }
  }

  implicit def environmentalDefiner(geneTaxonomy: GeneTaxonomy): Boolean = {
    geneTaxonomy match {
      case GeneTaxonomy.TEMPERATURE_GENE => true
      case GeneTaxonomy.PRESSURE_GENE => true
      case GeneTaxonomy.HUMIDITY_GENE => true
      case _ => false
    }
  }
}
