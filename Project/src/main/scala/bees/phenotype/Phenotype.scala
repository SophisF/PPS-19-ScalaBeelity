package bees.phenotype

import bees.phenotype.Characteristic.Characteristic
import bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

object Phenotype {

  trait Phenotype{
    val averageExpressions: Double
    def getCharacteristics: Set[Characteristic]
   // def getExpressionByTaxonomy[A](characteristicTaxonomy: CharacteristicTaxonomy): Option[A]
  }

  case class PhenotypeImpl(characteristics: Set[(Characteristic, Double)]) extends Phenotype {
    private var phenotype: Set[Characteristic] = characteristics map (_._1)
    override def getCharacteristics: Set[Characteristic] = this.phenotype
    override val averageExpressions: Double = this.characteristics.foldRight(0.0)(_._2 + _)/CharacteristicTaxonomy.maxId

   /* override def getExpressionByTaxonomy[A](characteristicTaxonomy: CharacteristicTaxonomy): Option[A] = this.phenotype.find(_.name.equals(characteristicTaxonomy)) match {
      case Some(a) => Some(a.expression)
      case _ => None
    }*/
  }

}
