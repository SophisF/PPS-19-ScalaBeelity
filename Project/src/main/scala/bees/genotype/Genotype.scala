package bees.genotype

import bees.genotype.Gene.Gene

object Genotype {
  trait Genotype {
    def getGenes : List[Gene]
  }

  case class GenotypeImpl(genes: List[Gene]) extends Genotype {
    require(genes.nonEmpty)
    private var genotype: List[Gene] = genes
    override def getGenes: List[Gene] = this.genotype
  }
}
