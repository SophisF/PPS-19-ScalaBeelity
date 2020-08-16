package bees.genotype

import bees.genotype.Gene._
import org.scalatest.funsuite.AnyFunSuite

class GeneTest(implicit convertToCoding: Gene => Option[Gene with CodingInformation]) extends AnyFunSuite {
  val temperatureGene: Gene = Gene(Some(GeneTaxonomy.TEMPERATURE_GENE))
  val genericGene: Gene = Gene()
  val nonCodingGene: Gene = Gene(Some(GeneTaxonomy.NON_CODING))

  private def isCoding(gene: Gene): Boolean = convertToCoding(gene) match {
    case Some(_) => true
    case _ => false
  }

  test("A Gene should have a name") {
    assert(genericGene.name != null)
  }

  test("A GeneTaxonomy should raise a NoSuchElementException if doesn't belong to GeneTaxonomy") {
    assertThrows[NoSuchElementException](GeneTaxonomy(GeneTaxonomy.maxId + 1))
  }

  test("A Coding Gene should have at least one information") {
    assert(isCoding(temperatureGene))
  }

  test("A Non Coding Gene shouldn't have any information"){
    assert(isCoding(nonCodingGene))
  }

  test("A generic Gene should have a valid name") {
    assert(GeneTaxonomy.values.contains(genericGene.name))
  }


}
