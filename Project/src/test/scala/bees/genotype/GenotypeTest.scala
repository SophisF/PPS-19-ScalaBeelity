package bees.genotype

import bees.genotype.Genotype.{Genotype, GenotypeImpl}
import org.scalatest.funsuite.AnyFunSuite

class GenotypeTest extends AnyFunSuite{
  val genotype: Genotype = GenotypeImpl()

  test("A Genotype should not be empty"){
    assert(genotype.genes.nonEmpty)
  }

  test("A Genotype should have exactly " + GeneTaxonomy.maxId + " Genes"){
    assert(genotype.genes.size == GeneTaxonomy.maxId)
  }


}
