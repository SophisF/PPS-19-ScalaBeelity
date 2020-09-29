package scala.model.bees.genotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.bee.Bee
import scala.model.bees.genotype.Gene.Gene
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.EnvironmentInformation
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.environment.adapter.Cell

class GenotypeTest extends AnyFunSuite {
  private val genotype: Genotype = Genotype()

  private val growthGene: Gene = Gene(GeneTaxonomy.GROWTH_GENE, 50)
  private val wingsGene: Gene = Gene(GeneTaxonomy.WINGS_GENE, 50)
  private val fixedGenotype: Genotype = Genotype(growthGene, wingsGene)

  val gen = "A Genotype"

  test(s"$gen should have exactly 8 genes") {
    assert(genotype.genes.size == 8)
  }

  test(s"$gen creates random genes if it's necessary"){
    assert(((Genotype(Gene(GeneTaxonomy TEMPERATURE_GENE)) genes) size) == 8)
  }

  test(s"$gen should contains exactly one gene of each type"){
    assert(((Genotype(Gene(GeneTaxonomy TEMPERATURE_GENE), Gene(GeneTaxonomy TEMPERATURE_GENE)) genes)
      count(_.taxonomy equals GeneTaxonomy.TEMPERATURE_GENE)) == 1)
  }

  test("The frequency expresses the frequency of a gene in the genotype"){
    assert((Genotype(Gene(GeneTaxonomy TEMPERATURE_GENE, 100)) frequencyOf GeneTaxonomy.TEMPERATURE_GENE) == 100)
  }

  test(s"$gen should expresses itself in a phenotype"){
    assert((genotype expressInPhenotype).isInstanceOf[Phenotype])
  }

  test("The average Genotype of a set of bees is made by the average of the frequency of their genes"){
    val genotype1 = Genotype()
    val genotype2 = Genotype()

    val bee1 = Bee(genotype1, genotype1 expressInPhenotype, 0, EnvironmentInformation(Seq(Cell())))
    val bee2 = Bee(genotype2, genotype2 expressInPhenotype, 0, EnvironmentInformation(Seq(Cell())))

    assert((Genotype averageGenotype Set(bee1, bee2) frequencyOf GeneTaxonomy.TEMPERATURE_GENE) ==
      ((genotype1 frequencyOf GeneTaxonomy.TEMPERATURE_GENE) + (genotype2 frequencyOf GeneTaxonomy.TEMPERATURE_GENE)) / 2)
  }

}
