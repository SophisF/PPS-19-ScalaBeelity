package model.bees.bee

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.genotype.Gene.GeneImpl
import scala.model.bees.genotype.GeneManager._
import scala.model.bees.genotype.{GeneTaxonomy, Genotype}
import scala.model.bees.genotype.Genotype.{Genotype, GenotypeImpl}
import scala.model.bees.phenotype.Phenotype.PhenotypeImpl

object EvolutionManager {

  def calculateAverageGenotype(bees: List[Bee]): Genotype = {
    GenotypeImpl(GeneTaxonomy.values.unsorted.map(value => GeneImpl(value, {
      bees.map(_.genotype.genes.toList.filter(_.name.equals(value)).head.frequency).sum / bees.size
    } )).toSet)
  }

  def calculateAveragePhenotype(genotype: Genotype): PhenotypeImpl = {
    PhenotypeImpl(Genotype.calculateExpression(genotype))
  }




}
