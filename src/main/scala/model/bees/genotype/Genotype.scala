package scala.model.bees.genotype


import scala.model.bees.bee.Bee.Bee
import scala.model.bees.genotype.Gene.Gene
import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.bees.phenotype.{CharacteristicTaxonomy, Phenotype}

/**
 * Object that represent the genotype.
 */
object Genotype {

  /**
   * Apply method for genotype.
   *
   * @param genes the input genes.
   * @return a new genotype.
   */
  def apply(genes: Set[Gene] = Set.empty): Genotype = GenotypeImpl(genes)

  /**
   * Method to calculate the average genotype in a sequence of bees.
   *
   * @param bees the set of bees.
   * @return the average genotype.
   */
  def averageGenotype(bees: Set[Bee]): Genotype = {
    Genotype(GeneTaxonomy.values.unsorted.map(value => Gene(value, {
      bees.toList.map(_.genotype.genes.toList.filter(_.name.equals(value)).head.frequency).sum / bees.size
    })))
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
      val geneOpt = genes.find(_.name.equals(geneTaxonomy))
      if (geneOpt.nonEmpty) geneOpt.get.frequency else Gene.minFrequency
    }

    /**
     * Method to calculate how a genotype expresses itself.
     *
     * @return a phenotype as expression of itself.
     */
    def expressInPhenotype: Phenotype = {
      Phenotype(CharacteristicTaxonomy.values.map(taxonomy => (taxonomy, this.genes.toList
        .filter(_.geneticInformation.influence(taxonomy).nonEmpty)
        .map(gene => gene.frequency * gene.geneticInformation.influence(taxonomy).get.influenceValue).sum
        / this.genes.toList.filter(_.geneticInformation.influence(taxonomy).nonEmpty).map(
        gene => gene.geneticInformation.influence(taxonomy).get.influenceValue).sum)).toMap)

    }
  }

  /**
   * Concrete implementation of genotype.
   *
   * @param geneSet a set of gene to build the genotype.
   */
  private case class GenotypeImpl(geneSet: Set[Gene]) extends Genotype {
    override val genes: Set[Gene] = GeneTaxonomy.values.unsorted.map(value => geneSet.find(_.name.equals(value)).getOrElse(Gene(value)))
  }

}
