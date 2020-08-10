package bees

import bees.Genotype.{Genotype, GenotypeImpl}
import org.scalatest.funsuite.AnyFunSuite

class GenotypeTest extends AnyFunSuite{
  val genotype: Genotype = GenotypeImpl()

  test("A Genotype should have exactly a gene of each type"){
    assert(genotype.getGenes.size == 7)
  }
}
