package scala.model.bees.genotype

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.HashSet
import scala.model.bees.genotype.Gene.Gene
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

class GenotypeTest extends AnyFunSuite {
  private val genotype: Genotype = Genotype()

  private val growthGene: Gene = Gene(GeneTaxonomy.GROWTH_GENE, 50)
  private val wingsGene: Gene = Gene(GeneTaxonomy.WINGS_GENE, 50)
  private val fixedGenotype: Genotype = Genotype(HashSet(growthGene, wingsGene))

  private def calculateExpression(gene: Gene, taxonomy: CharacteristicTaxonomy): Double =
    gene.frequency * gene.geneticInformation.influence(taxonomy).get.influenceValue

  test("A Genotype should not be empty") {
    assert(genotype.genes.nonEmpty)
  }

  test("A Genotype should have exactly " + GeneTaxonomy.maxId + " Genes") {
    assert(genotype.genes.size == GeneTaxonomy.maxId)
  }

  test("The Genotype expresses its Genes as the sum of their Genetic Information for each Characteristic") {
    val taxonomy: CharacteristicTaxonomy = CharacteristicTaxonomy.SPEED
    assert(Genotype.calculateExpression(fixedGenotype)(taxonomy).toInt
      == (this.calculateExpression(growthGene, taxonomy)
      + this.calculateExpression(wingsGene, taxonomy)).toInt)
  }


}
