package model.bees.genotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.{GeneTaxonomy, GeneticEvolver, Genotype}
import scala.model.bees.genotype.Genotype.{Genotype, GenotypeImpl}
import scala.model.bees.phenotype.Phenotype.{Phenotype, PhenotypeImpl}

class GeneticEvolverTest extends AnyFunSuite{
  val genotype: Genotype = GenotypeImpl()
  val phenotype: Phenotype = PhenotypeImpl(Genotype.calculateExpression(genotype))

  test("The evolution should slowly adapt the bees to the environment"){
    val newGenes = GeneticEvolver.buildGenotype(genotype)(phenotype)(40)(1080)(100)
    assert(newGenes.find(_.name.equals(GeneTaxonomy.TEMPERATURE_GENE)).get.frequency
      >= genotype.genes.find(_.name.equals(GeneTaxonomy.TEMPERATURE_GENE)).get.frequency)
  }
}
