package scala.model.bees.bee

import model.bees.bee.EvolutionManager
import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.genotype.{GeneTaxonomy, Genotype}
import scala.model.bees.phenotype.Phenotype
import scala.model.bees.phenotype.Phenotype.Phenotype

class EvolutionManagerTest extends AnyFunSuite{
  val genotype: Genotype = Genotype()
  val phenotype: Phenotype = Phenotype(Genotype.calculateExpression(genotype))

  test("The evolution should slowly adapt the bees to the environment"){
    val newGenotype = EvolutionManager.buildGenotype(genotype)(phenotype)(40)(1080)(100)(1)
    assert(newGenotype.frequency(GeneTaxonomy.TEMPERATURE_GENE) >= genotype.frequency(GeneTaxonomy.TEMPERATURE_GENE))
  }

  test("The evolution should slowly change a non environmental gene random, with a factor of the double of the square of the time"){
    val time = 1
    val newGenotype = EvolutionManager.buildGenotype(genotype)(phenotype)(40)(1080)(100)(time)
    val newGrowthGeneFrequency = newGenotype.frequency(GeneTaxonomy.GROWTH_GENE)
    val oldGrowthGeneFrequency = genotype.frequency(GeneTaxonomy.GROWTH_GENE)
    assert(newGrowthGeneFrequency == oldGrowthGeneFrequency - 2 * math.sqrt(time).toInt
      || newGrowthGeneFrequency == oldGrowthGeneFrequency + 2 * math.sqrt(time).toInt)
  }
}
