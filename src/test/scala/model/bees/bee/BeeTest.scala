package scala.model.bees.bee

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.genotype.Genotype
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.EnvironmentInformation
import scala.model.environment.adapter.Cell

class BeeTest extends AnyFunSuite{
  private val genotype: Genotype = Genotype()
  private val bee1: Bee = Bee(genotype, genotype expressInPhenotype, 5, EnvironmentInformation(Seq(Cell())))
  private val bee2: Bee = Bee(genotype, genotype expressInPhenotype, 121, EnvironmentInformation(Seq(Cell())))

  test(s"The bee's age must be less or equal than its longevity"){
    assert(bee1.age <= bee1.effectiveLongevity)
  }

  test(s"When the bee has age more or equal than its longevity its not alive"){
    assert(!bee2.isAlive)
  }

}
