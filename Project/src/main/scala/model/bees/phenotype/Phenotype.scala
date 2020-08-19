package model.bees.phenotype

import model.bees.phenotype.Characteristic.Characteristic
import model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

object Phenotype {

  trait Phenotype{
    val characteristics: Set[Characteristic]
    def characteristicByTaxonomy(characteristicTaxonomy: CharacteristicTaxonomy): Option[Characteristic]
  }

  case class PhenotypeImpl(expressions: Map[CharacteristicTaxonomy, Double]) extends Phenotype {
    require(expressions.size == CharacteristicTaxonomy.maxId)
    override val characteristics: Set[Characteristic] = expressions.map(kv => Characteristic(kv._1, kv._2) ).toSet


    override def characteristicByTaxonomy(characteristicTaxonomy: CharacteristicTaxonomy): Option[Characteristic] =
      this.characteristics find(_.name.equals(characteristicTaxonomy))
  }

}
