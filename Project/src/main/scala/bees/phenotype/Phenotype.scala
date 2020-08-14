package bees.phenotype

import bees.phenotype.Characteristic._
import bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

object Phenotype {

  trait Phenotype{
    val averageExpressions: Double
    def getCharacteristics: Set[Characteristic]
    def getCharacteristicByTaxonomy(characteristicTaxonomy: CharacteristicTaxonomy): Option[Characteristic]
  }

  case class PhenotypeImpl(characteristics: Set[(CharacteristicTaxonomy, Double)]) extends Phenotype {
    private var phenotype: Set[Characteristic] = characteristics map (c => Characteristic(c._1, averageExpressions, c._2) )
    override def getCharacteristics: Set[Characteristic] = this.phenotype
    override val averageExpressions: Double = this.characteristics.foldRight(0.0)(_._2 + _)/CharacteristicTaxonomy.maxId

    override def getCharacteristicByTaxonomy(characteristicTaxonomy: CharacteristicTaxonomy): Option[Characteristic] =
      this.phenotype.find(_.name.equals(characteristicTaxonomy))
  }

}
