package bees.genotype

import bees.genotype.Gene.Gene

/**
 * Object that represents genotype
 */
object Genotype {

  /**
   * Trait that represents genotype
   */
  trait Genotype {
    def getGenes : List[Gene]
  }

  /**
   * Class that represents genotype
   * @param genes a list of genes
   */
  case class GenotypeImpl(genes: List[Gene]) extends Genotype {
    require(genes.nonEmpty)
    private var genotype: List[Gene] = genes
    override def getGenes: List[Gene] = this.genotype
  }
}
