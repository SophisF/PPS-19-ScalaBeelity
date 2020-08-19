package bees.genotype

import bees.genotype.Gene._
import org.scalatest.funsuite.AnyFunSuite
import bees.genotype.GeneManager._

class GeneTest extends AnyFunSuite {
  val temperatureGene: Gene = GeneImpl(GeneTaxonomy.TEMPERATURE_GENE)
  val negativeFrequencyGene: Gene = GeneImpl(GeneTaxonomy.TEMPERATURE_GENE, freq = -1)
  val overMaxFrequencyGene: Gene = GeneImpl(GeneTaxonomy.PRESSURE_GENE, freq = Gene.maxFrequency + 1)


  test("A Gene should have a name") {
    assert(temperatureGene.name != null)
  }

  test("A GeneTaxonomy should raise a NoSuchElementException if doesn't belong to GeneTaxonomy") {
    assertThrows[NoSuchElementException](GeneTaxonomy(GeneTaxonomy.maxId + 1))
  }

  test("A generic Gene should have a valid name") {
    assert(GeneTaxonomy.values.contains(temperatureGene.name))
  }

  test("A Gene should have always positive frequency"){
    assert(negativeFrequencyGene.frequency > 0)
  }

  test("A Gene should have always frequency in range (1, 100)"){
    assert(negativeFrequencyGene.frequency >= 1 && overMaxFrequencyGene.frequency <= 100)
  }

  test("A Gene should have at least one information") {
    assert(temperatureGene.geneticInformation.information.nonEmpty)
  }




}
