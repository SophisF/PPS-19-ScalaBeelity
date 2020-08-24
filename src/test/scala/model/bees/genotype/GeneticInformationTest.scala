package scala.model.bees.genotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.GeneticInformation.GeneticInformation
import scala.model.bees.phenotype.CharacteristicTaxonomy

class GeneticInformationTest extends AnyFunSuite{
  val geneticInformation: GeneticInformation = GeneticInformation((CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY,
                                                                      Influence()))

  test("A GeneticInformation should not be empty"){
    assert(geneticInformation.information.nonEmpty)
  }

  test("An empty GeneticInformation should throws an IllegalArgumentException"){
    assertThrows[IllegalArgumentException](GeneticInformation())
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
