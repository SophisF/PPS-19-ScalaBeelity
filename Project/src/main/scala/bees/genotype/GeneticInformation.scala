package bees.genotype

import bees.genotype.Influence.Influence
import bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

object GeneticInformation {

  trait GeneticInformation {

    def getInfo: Map[CharacteristicTaxonomy, Influence]

    def getCharacteristics: Iterable[CharacteristicTaxonomy] = this.getInfo.keys

    def getInfluence(elem: CharacteristicTaxonomy): Option[Influence] =
      if (this.getInfo.contains(elem)) Some(this.getInfo(elem)) else None
  }

  case class GeneticInformationImpl(info: (CharacteristicTaxonomy, Influence)*) extends GeneticInformation {
    require(info.nonEmpty)
    val information: Map[CharacteristicTaxonomy, Influence] = info.toMap

    override def getInfo: Map[CharacteristicTaxonomy, Influence] = this.information
  }

}