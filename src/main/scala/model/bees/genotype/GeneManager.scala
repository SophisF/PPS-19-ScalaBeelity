package scala.model.bees.genotype

import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.genotype.GeneticInformation.{GeneticInformation, GeneticInformationImpl}
import scala.model.bees.genotype.Influence.InfluenceImpl
import scala.model.bees.phenotype.CharacteristicTaxonomy

/**
 * Singleton that implements strategies to define the genes.
 */
object GeneManager {

  /**
   * Implicit strategy which binds a gene taxonomy to its genetic information.
   * @param geneTaxonomy the taxonomy of the gene.
   * @return a genetic information.
   */
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


  //TODO forse più corretto che prenda una informazione.
  /**
   * Implicit strategy that defines which gene has influence on the environmental characteristics.
   * @param geneTaxonomy the taxonomy of the gene.
   * @return a boolean, true if the gene influence the adaptation to the environment, false otherwise.
   */
  implicit def environmentalDefiner(geneTaxonomy: GeneTaxonomy): Boolean = {
    geneTaxonomy match {
      case GeneTaxonomy.TEMPERATURE_GENE => true
      case GeneTaxonomy.PRESSURE_GENE => true
      case GeneTaxonomy.HUMIDITY_GENE => true
      case _ => false
    }
  }
}
