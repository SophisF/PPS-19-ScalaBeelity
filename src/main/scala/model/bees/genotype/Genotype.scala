package scala.model.bees.genotype


import scala.model.bees.genotype.Gene.{Gene, GeneImpl}
import scala.model.bees.genotype.GeneManager._
import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

/**
 * Object that represent the genotype.
 */
object Genotype {

  /**
   * Utility method to calculate how a genotype expresses itself.
   * @param genotype the genotype.
   * @return a map which binds a characteristic taxonomy with his expression value, expressed as a double.
   */
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

    /**
     * Method to get the frequency of a gene.
     * @param geneTaxonomy the gene taxonomy.
     * @return the frequency of the gene.
     */
    def frequency(geneTaxonomy: GeneTaxonomy): Int = {
      val geneOpt = genes.find(_.name.equals(geneTaxonomy))
      if(geneOpt.nonEmpty) geneOpt.get.frequency else Gene.minFrequency
    }

  }

  /**
   * Concrete implementation of genotype.
   * @param geneSet a set of gene to build the genotype.
   */
  case class GenotypeImpl(geneSet: Set[Gene] = Set.empty) extends Genotype {
    override val genes: Set[Gene] = GeneTaxonomy.values.unsorted.map(value => geneSet.find(_.name.equals(value)).getOrElse(GeneImpl(value)))
  }

}
