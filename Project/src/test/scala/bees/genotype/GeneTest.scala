package bees.genotype

import bees.genotype.Gene._
import org.scalatest.funsuite.AnyFunSuite
import bees.genotype.GeneManager._

class GeneTest extends AnyFunSuite {
  val temperatureGene: Gene = GeneImpl(GeneTaxonomy.TEMPERATURE_GENE)


  test("A Gene should have a name") {
    assert(temperatureGene.name != null)
  }

  test("A GeneTaxonomy should raise a NoSuchElementException if doesn't belong to GeneTaxonomy") {
    assertThrows[NoSuchElementException](GeneTaxonomy(GeneTaxonomy.maxId + 1))
  }

  test("A generic Gene should have a valid name") {
    assert(GeneTaxonomy.values.contains(temperatureGene.name))
  }

  test("A Coding Gene should have at least one information") {
    assert(temperatureGene.geneticInformation.information.nonEmpty)
  }




}
