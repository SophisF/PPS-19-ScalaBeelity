package scala.model.bees.genotype

import scala.model.bees.genotype.Influence.Influence
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

/**
The genetic information of the bees.
 */
object GeneticInformation {

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
     * @return the characteristics expressed by the genetic information
     */
    def characteristics: Iterable[CharacteristicTaxonomy] = this.information.keys

    /**
     * Getter of the influence of a genetic information
     * @param taxonomy the characteristic taxonomy
     * @return an optional that contains the influence, if the genetic information express the taxonomy, an empty optional otherwise
     */
    def influence(taxonomy: CharacteristicTaxonomy): Option[Influence] =
      if (this.information.contains(taxonomy)) Some(this.information(taxonomy)) else None
  }

  /**
   * Class that represent a genetic information
   * @constructor creates a genetic information from tuples of characteristic taxonomy and influence
   * @param info tuples of characteristic taxonomy and influence
   */
  case class GeneticInformationImpl(info: (CharacteristicTaxonomy, Influence)*) extends GeneticInformation {
    require(info.nonEmpty)
    val information: Map[CharacteristicTaxonomy, Influence] = info.toMap
  }

}