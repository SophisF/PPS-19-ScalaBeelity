package scala.model.bees.bee

import org.scalatest.funsuite.AnyFunSuite
import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.genotype.Genotype
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.EnvironmentInformation
import scala.model.environment.adapter.Cell
import scala.utility.Point

class QueenTest extends AnyFunSuite{
  private val genotype: Genotype = Genotype()
  private val queen: Queen = Queen(None, genotype, genotype expressInPhenotype, 0,  Point(10,20), null, EnvironmentInformation(Seq(Cell())))

  test(s"Queen must be an instance of Bee"){
    assert(queen.isInstanceOf[Bee])
  }

  test(s"The queen must have a colony"){
    assert((queen.colony != null))
  }

  test(s"At the beginning the colony created by the queen must have fewer bees than its lognevity rate"){
    assert(queen.colony.bees.size <= queen.effectiveReproductionRate + 2)
  }

}
