package bees

import bees.genotype.Gene
import bees.genotype.Genotype.{Genotype, GenotypeImpl}
import org.scalatest.funsuite.AnyFunSuite

class GenotypeTest extends AnyFunSuite{
  val genotype: Genotype = GenotypeImpl(List.range(0, 100) map (_ => Gene()))

  test("A empty Genotype should throws an IllegalArgumentException"){
    //assert(genotype.getGenes.nonEmpty)
    assertThrows[IllegalArgumentException](GenotypeImpl(List()))
  }

  test("A Genotype should have exactly " + 100 + " Genes"){
    assert(genotype.getGenes.size == 100)
  }


}
