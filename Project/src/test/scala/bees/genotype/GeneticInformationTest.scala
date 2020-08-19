package bees.genotype

import bees.genotype.GeneticInformation.{GeneticInformation, GeneticInformationImpl}
import bees.genotype.Influence.InfluenceImpl
import bees.phenotype.CharacteristicTaxonomy
import org.scalatest.funsuite.AnyFunSuite

class GeneticInformationTest extends AnyFunSuite{
  val geneticInformation: GeneticInformation = GeneticInformationImpl((CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY,
                                                                      InfluenceImpl()))

  test("A GeneticInformation should not be empty"){
    assert(geneticInformation.information.nonEmpty)
  }

  test("An empty GeneticInformation should throws an IllegalArgumentException"){
    assertThrows[IllegalArgumentException](GeneticInformationImpl())
  }

  test("A GeneticInformation should contain exactly the initializer Taxonomy"){
    assert(!geneticInformation.characteristics.toSeq.contains(CharacteristicTaxonomy.AGGRESSION_RATE))
  }

  test("A GenericInformation should return an empty optional if doesn't contains a Taxonomy"){
    assert(geneticInformation.influence(CharacteristicTaxonomy.AGGRESSION_RATE) match {
      case None => true
      case _ => false
    })
  }

}
