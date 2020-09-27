package scala.model.bees.genotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Gene.Gene
import scala.model.bees.genotype.Genotype.Genotype

class GenotypeTest extends AnyFunSuite {
  private val genotype: Genotype = Genotype()

  private val growthGene: Gene = Gene(GeneTaxonomy.GROWTH_GENE, 50)
  private val wingsGene: Gene = Gene(GeneTaxonomy.WINGS_GENE, 50)
  private val fixedGenotype: Genotype = Genotype(growthGene, wingsGene)

  /*
  private def calculateExpression(gene: Gene, taxonomy: CharacteristicTaxonomy): Int =
    (gene.frequency * gene.geneticInformation.influence(taxonomy).get.influenceValue).toInt /
      gene.geneticInformation.influence(taxonomy)

   */

  test("A Genotype should not be empty") {
    assert(genotype.genes.nonEmpty)
  }

  test("A Genotype should have exactly " + GeneTaxonomy.maxId + " Genes") {
    assert(genotype.genes.size == GeneTaxonomy.maxId)
  }

  /*
  test("The Genotype expresses its Genes as the sum of their Genetic Information for each Characteristic") {
    val taxonomy: CharacteristicTaxonomy = CharacteristicTaxonomy.SPEED
    assert(fixedGenotype.expressItself.expressionOf(taxonomy) ==
      (this.calculateExpression(growthGene, taxonomy)
      + this.calculateExpression(wingsGene, taxonomy)))
  }

   */


}
