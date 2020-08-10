package bees

import bees.Genotype.{Genotype, GenotypeImpl}
import org.scalatest.funsuite.AnyFunSuite

class GenotypeTest extends AnyFunSuite{
  val genotype: Genotype = GenotypeImpl(temperatureCompatibilityGene = Gene("Aggression"))

  genotype.getGenes.foreach(g => println(g))

  test("A Genotype should have exactly 7 genes"){
    assert(genotype.getGenes.size == 7)
  }

  test("A Genotype should have exactly a gene of each type"){
    val names = for {
      gene <- genotype.getGenes
    } yield gene.name
  }
}
