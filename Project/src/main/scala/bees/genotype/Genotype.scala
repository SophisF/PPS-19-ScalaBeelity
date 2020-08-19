package bees.genotype


import bees.genotype.Gene.{Gene, GeneImpl}
import bees.genotype.GeneManager._
import bees.phenotype.CharacteristicTaxonomy
import bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

import scala.collection.immutable.HashSet

/**
 * Object that represent the genotype.
 */
object Genotype {

  def calculateExpression(genotype: Genotype): Map[CharacteristicTaxonomy, Double] = {
    CharacteristicTaxonomy.values.map(taxonomy => (taxonomy, genotype.genes
      .filter(_.geneticInformation.influence(taxonomy).nonEmpty)
      .map(gene => gene.frequency * gene.geneticInformation.influence(taxonomy).get.influenceValue).foldRight(0.0)(_ + _))).toMap
  }


  /**
   * Trait for the genotype.
   */
  trait Genotype {
    val genes: Set[Gene]
  }


  case class GenotypeImpl(geneSet: Set[Gene] = HashSet()) extends Genotype {
    override val genes: Set[Gene] = GeneTaxonomy.values map (value => geneSet.find(_.name.equals(value)) getOrElse GeneImpl(value))

  }

}
