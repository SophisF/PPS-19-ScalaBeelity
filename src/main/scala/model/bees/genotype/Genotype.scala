package scala.model.bees.genotype


import scala.model.bees.bee.Bee.Bee
import scala.model.bees.genotype.Gene.Gene
import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.bees.phenotype.{CharacteristicTaxonomy, Phenotype}
import scala.utility.PimpIterable._

/**
 * Object that represent the genotype.
 */
private[model] object Genotype {

  /**
   * Apply method for genotype.
   *
   * @param genes the input genes.
   * @return a new genotype.
   */
  def apply(genes: Gene*): Genotype = GenotypeImpl(genes: _ *)

  /**
   * Method to calculate the average genotype in a sequence of bees.
   *
   * @param bees the set of bees.
   * @return the average genotype.
   */
  def averageGenotype(bees: Set[Bee]): Genotype = {
    Genotype(((GeneTaxonomy values) unsorted) map (value => Gene(value, {
      (bees toList) map (bee => ((((bee genotype) genes) toList) filter (_.taxonomy equals value ) head) frequency) average
    })) toSeq: _*)
  }

  /**
   * Trait for the genotype.
   */
  trait Genotype {
    val genes: Set[Gene]

    /**
     * Method to get the frequency of a gene.
     *
     * @param geneTaxonomy the gene taxonomy.
     * @return the frequency of the gene.
     */
    def frequencyOf(geneTaxonomy: GeneTaxonomy): Int = {
      val geneOpt = genes find (_.taxonomy equals geneTaxonomy)
      if (geneOpt nonEmpty) (geneOpt get) frequency else Gene minFrequency
    }

    /**
     * Method to calculate how a genotype expresses itself.
     *
     * @return a phenotype as expression of itself.
     */
    def expressInPhenotype: Phenotype = {
      Phenotype(((CharacteristicTaxonomy values) map (taxonomy => (taxonomy, {
        val relatedGenes = ((this genes) toList) filter (_.geneticInformation influence taxonomy nonEmpty)
        (relatedGenes map (gene => (gene frequency) * (((gene geneticInformation) influence taxonomy get) influenceValue)) sum) /
          (relatedGenes map (gene => ((gene geneticInformation) influence taxonomy get) influenceValue) sum)
      }))).toMap)
    }
  }

  /**
   * Concrete implementation of genotype.
   *
   * @param geneArg a variable arguments of gene to build the genotype.
   */
  private case class GenotypeImpl(geneArg: Gene*) extends Genotype {
    override val genes: Set[Gene] = ((GeneTaxonomy values) unsorted) map
      (taxonomy => geneArg find(_.taxonomy equals taxonomy) getOrElse Gene(taxonomy))
  }

}
