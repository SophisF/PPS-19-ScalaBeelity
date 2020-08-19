package scala.model.bees.genotype


import scala.model.bees.genotype.Gene.{Gene, GeneImpl}
import scala.model.bees.genotype.GeneManager._
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

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


  case class GenotypeImpl(geneSet: Set[Gene] = Set.empty) extends Genotype {
    override val genes: Set[Gene] = GeneTaxonomy.values.unsorted.map(value => geneSet.find(_.name.equals(value)).getOrElse(GeneImpl(value)))

  }

}
