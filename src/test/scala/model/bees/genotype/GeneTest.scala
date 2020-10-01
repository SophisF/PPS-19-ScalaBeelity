package scala.model.bees.genotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Gene._

class GeneTest extends AnyFunSuite {
  private val temperatureGene: Gene = Gene(GeneTaxonomy.TEMPERATURE_GENE)
  private val negativeFrequencyGene: Gene = Gene(GeneTaxonomy.TEMPERATURE_GENE, -1)
  private val overMaxFrequencyGene: Gene = Gene(GeneTaxonomy.PRESSURE_GENE, Gene.maxFrequency + 1)
  private val nonEnvironmentalGene: Gene = Gene(GeneTaxonomy.GROWTH_GENE)

  private val geneStr = "A Gene should always have"

  test("There are 8 different type of genes"){
    assert(GeneTaxonomy.maxId == 8)
  }

  test(s"$geneStr a name") {
    assert(temperatureGene.taxonomy != null)
  }

  test(s"$geneStr a valid name") {
    assert(GeneTaxonomy.values.contains(temperatureGene.taxonomy))
  }

  test(s"$geneStr frequency in range (1, 100)"){
    assert(negativeFrequencyGene.frequency >= 1 && overMaxFrequencyGene.frequency <= 100)
  }

  test(s"$geneStr at least one information") {
    assert(temperatureGene.geneticInformation.information.nonEmpty)
  }

  test("A Gene that expresses environmental characteristics should be an environmental Gene"){
    assert(temperatureGene.isEnvironmental)
  }

  test("A Gene that doesn't express environmental characteristics shouldn't be an environmental Gene"){
    assert(!nonEnvironmentalGene.isEnvironmental)
  }





}
