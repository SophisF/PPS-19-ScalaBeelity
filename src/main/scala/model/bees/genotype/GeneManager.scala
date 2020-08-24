package scala.model.bees.genotype

import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.genotype.GeneticInformation.GeneticInformation
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
      case GeneTaxonomy.TEMPERATURE_GENE => GeneticInformation((CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, Influence()))
      case GeneTaxonomy.PRESSURE_GENE => GeneticInformation((CharacteristicTaxonomy.PRESSURE_COMPATIBILITY, Influence()))
      case GeneTaxonomy.HUMIDITY_GENE => GeneticInformation((CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY, Influence()))
      case GeneTaxonomy.AGGRESSION_GENE => GeneticInformation((CharacteristicTaxonomy.AGGRESSION_RATE, Influence()))
      case GeneTaxonomy.REPRODUCTION_GENE => GeneticInformation((CharacteristicTaxonomy.REPRODUCTION_RATE,
        Influence()))
      case GeneTaxonomy.LONGEVITY_GENE => GeneticInformation((CharacteristicTaxonomy.LONGEVITY, Influence()))
      case GeneTaxonomy.COLOR_GENE => GeneticInformation((CharacteristicTaxonomy.COLOR, Influence()))
      case GeneTaxonomy.GROWTH_GENE => GeneticInformation((CharacteristicTaxonomy.SPEED, Influence(
        typeOfInfluence = InfluenceType.NEGATIVE,
        influenceInPercentage = 10
      )), (CharacteristicTaxonomy.AGGRESSION_RATE, Influence()))
      case _ => GeneticInformation((CharacteristicTaxonomy.SPEED, Influence()))
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
