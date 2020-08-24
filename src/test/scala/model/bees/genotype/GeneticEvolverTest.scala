package model.bees.genotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.{GeneTaxonomy, GeneticEvolver, Genotype}
import scala.model.bees.genotype.Genotype.{Genotype, GenotypeImpl}
import scala.model.bees.phenotype.Phenotype.{Phenotype, PhenotypeImpl}

class GeneticEvolverTest extends AnyFunSuite{
  val genotype: Genotype = GenotypeImpl()
  val phenotype: Phenotype = PhenotypeImpl(Genotype.calculateExpression(genotype))

  test("The evolution should slowly adapt the bees to the environment"){
    val newGenotype = GenotypeImpl(GeneticEvolver.buildGenotype(genotype)(phenotype)(40)(1080)(100))
    assert(newGenotype.frequency(GeneTaxonomy.TEMPERATURE_GENE) >= genotype.frequency(GeneTaxonomy.TEMPERATURE_GENE))
  }

  test("The evolution should slowly change a non environmental gene random, with a factor of 2"){
    val newGenotype = GenotypeImpl(GeneticEvolver.buildGenotype(genotype)(phenotype)(40)(1080)(100))
    val newGrowthGeneFrequency = newGenotype.frequency(GeneTaxonomy.GROWTH_GENE)
    val oldGrowthGeneFrequency = genotype.frequency(GeneTaxonomy.GROWTH_GENE)
    assert(newGrowthGeneFrequency == oldGrowthGeneFrequency - 2 || newGrowthGeneFrequency == oldGrowthGeneFrequency + 2)
  }
}
