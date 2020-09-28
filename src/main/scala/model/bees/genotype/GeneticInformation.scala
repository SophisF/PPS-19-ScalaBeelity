package scala.model.bees.genotype

import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.genotype.Influence.Influence
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

/**
 * The genetic information of the bees.
 */
object GeneticInformation {

  /**
   * A default mapper for the genetic information.
   *
   * @param taxonomy the gene taxonomy.
   * @return the genetic information wrapped inside a specific gene.
   */
  private def defaultMapper(taxonomy: GeneTaxonomy): GeneticInformation = taxonomy match {
    case GeneTaxonomy.TEMPERATURE_GENE => GeneticInformationImpl((CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, Influence()))
    case GeneTaxonomy.PRESSURE_GENE => GeneticInformationImpl((CharacteristicTaxonomy.PRESSURE_COMPATIBILITY, Influence()))
    case GeneTaxonomy.HUMIDITY_GENE => GeneticInformationImpl((CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY, Influence()))
    case GeneTaxonomy.AGGRESSION_GENE => GeneticInformationImpl((CharacteristicTaxonomy.AGGRESSION_RATE, Influence()))
    case GeneTaxonomy.REPRODUCTION_GENE => GeneticInformationImpl((CharacteristicTaxonomy.REPRODUCTION_RATE,
      Influence()))
    case GeneTaxonomy.LONGEVITY_GENE => GeneticInformationImpl((CharacteristicTaxonomy.LONGEVITY, Influence()))
    case GeneTaxonomy.GROWTH_GENE => GeneticInformationImpl((CharacteristicTaxonomy.SPEED, Influence(
      typeOfInfluence = InfluenceType.NEGATIVE,
      influenceInPercentage = 10
    )), (CharacteristicTaxonomy.AGGRESSION_RATE, Influence()))
    case _ => GeneticInformationImpl((CharacteristicTaxonomy.SPEED, Influence()))
  }

  /**
   * Apply method for the genetic information.
   *
   * @param taxonomy the gene taxonomy.
   * @return a new genetic information.
   */
  def apply(taxonomy: GeneTaxonomy, mapper: GeneTaxonomy => GeneticInformation = this defaultMapper): GeneticInformation = mapper(taxonomy)


  /**
   * Trait that represent a genetic information.
   */
  trait GeneticInformation {

    /**
     * The information binds a CharacteristicTaxonomy with its own Influence.
     */
    val information: Map[CharacteristicTaxonomy, Influence]

    /**
     * Getter of the characteristics of a genetic information.
     *
     * @return the characteristics expressed by the genetic information
     */
    def characteristics: Iterable[CharacteristicTaxonomy] = (this information) keys

    /**
     * Getter of the influence of a genetic information
     *
     * @param taxonomy the characteristic taxonomy
     * @return an optional that contains the influence, if the genetic information express the taxonomy, an empty optional otherwise
     */
    def influence(taxonomy: CharacteristicTaxonomy): Option[Influence] =
      if ((this information) contains taxonomy ) Some(this information taxonomy) else None
  }

  /**
   * Class that represent a genetic information
   *
   * @constructor creates a genetic information from tuples of characteristic taxonomy and influence
   * @param info tuples of characteristic taxonomy and influence
   */
  private case class GeneticInformationImpl(info: (CharacteristicTaxonomy, Influence)*) extends GeneticInformation {
    override val information: Map[CharacteristicTaxonomy, Influence] = info toMap
  }

}