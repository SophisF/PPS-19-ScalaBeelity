package scala.model.bees.genotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Gene._

class GeneTest extends AnyFunSuite {
  val temperatureGene: Gene = Gene(GeneTaxonomy.TEMPERATURE_GENE)
  val negativeFrequencyGene: Gene = Gene(GeneTaxonomy.TEMPERATURE_GENE, freq = -1)
  val overMaxFrequencyGene: Gene = Gene(GeneTaxonomy.PRESSURE_GENE, freq = Gene.maxFrequency + 1)
  val nonEnvironmentalGene: Gene = Gene(GeneTaxonomy.GROWTH_GENE)

  private val aGeneShouldHave = "A Gene should have"

  test("A GeneTaxonomy should raise a NoSuchElementException if doesn't belong to GeneTaxonomy") {
    assertThrows[NoSuchElementException](GeneTaxonomy(GeneTaxonomy.maxId + 1))
  }

  test("There are 8 different type of genes"){
    assert(GeneTaxonomy.maxId == 8)
  }

  test(s"$aGeneShouldHave a name") {
    assert(temperatureGene.name != null)
  }

  test(s"$aGeneShouldHave a valid name") {
    assert(GeneTaxonomy.values.contains(temperatureGene.name))
  }

  test(s"$aGeneShouldHave always positive frequency"){
    assert(negativeFrequencyGene.frequency > 0)
  }

  test(s"$aGeneShouldHave always frequency in range (1, 100)"){
    assert(negativeFrequencyGene.frequency >= 1 && overMaxFrequencyGene.frequency <= 100)
  }

  test(s"$aGeneShouldHave at least one information") {
    assert(temperatureGene.geneticInformation.information.nonEmpty)
  }

  test("A Gene that expresses environmental characteristics should be an environmental Gene"){
    assert(temperatureGene.isEnvironmental)
  }

  test("A Gene that doesn't express environmental characteristics shouldn't be an environmental Gene"){
    assert(!nonEnvironmentalGene.isEnvironmental)
  }





}