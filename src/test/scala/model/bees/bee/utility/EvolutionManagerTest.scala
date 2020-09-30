package scala.model.bees.bee.utility

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.genotype.{GeneTaxonomy, Genotype}
import scala.model.bees.phenotype.EnvironmentInformation
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.environment.adapter.Cell

class EvolutionManagerTest extends AnyFunSuite{
  val genotype: Genotype = Genotype()
  val phenotype: Phenotype = genotype expressInPhenotype

  test("The evolution should slowly adapt the bees to the environment"){
    val newGenotype = EvolutionManager.evolveGenotype(genotype)(EnvironmentInformation(Seq(Cell(40))))(1)
    assert((newGenotype frequencyOf GeneTaxonomy.TEMPERATURE_GENE) >= (genotype frequencyOf GeneTaxonomy.TEMPERATURE_GENE))
  }

  test("The evolution should slowly change a non environmental gene random, with a factor of the square of the time"){
    val time = 4
    val newGenotype = EvolutionManager.evolveGenotype(genotype)(EnvironmentInformation(Seq(Cell())))(time)
    val newGrowthGeneFrequency = newGenotype frequencyOf GeneTaxonomy.GROWTH_GENE
    val oldGrowthGeneFrequency = genotype frequencyOf GeneTaxonomy.GROWTH_GENE
    assert(newGrowthGeneFrequency == oldGrowthGeneFrequency - math.sqrt(time).toInt
      || newGrowthGeneFrequency == oldGrowthGeneFrequency + math.sqrt(time).toInt)
  }
}
