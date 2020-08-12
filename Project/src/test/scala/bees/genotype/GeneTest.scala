package bees.genotype

import bees.genotype.Gene._
import org.scalatest.funsuite.AnyFunSuite

class GeneTest extends AnyFunSuite{
  val temperatureGene: Gene = Gene(Some(GeneTaxonomy.TEMPERATURE_GENE))
  val genericGene: Gene = Gene()

  test("A Gene should have a name"){
    assert(temperatureGene.name != null)
  }

  test("A Gene should have at least one information"){
    assert(temperatureGene.information.getCharacteristics.nonEmpty)
  }

  test("A generic Gene should have a valid name"){
    assert(GeneTaxonomy.values.contains(genericGene.name))
  }



}
