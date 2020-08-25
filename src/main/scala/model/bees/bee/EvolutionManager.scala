package model.bees.bee

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.genotype.Genotype.{Genotype, GenotypeImpl}
import scala.model.bees.genotype.{Gene, GeneTaxonomy, Genotype}
import scala.model.bees.phenotype.Phenotype
import scala.model.bees.phenotype.Phenotype.{Phenotype}

object EvolutionManager {

  def calculateAverageGenotype(bees: List[Bee]): Genotype = {
    GenotypeImpl(GeneTaxonomy.values.unsorted.map(value => Gene(value, {
      bees.map(_.genotype.genes.toList.filter(_.name.equals(value)).head.frequency).sum / bees.size
    } )).toSet)
  }

  def calculateAveragePhenotype(genotype: Genotype): Phenotype = {
    Phenotype(Genotype.calculateExpression(genotype))
  }

}
