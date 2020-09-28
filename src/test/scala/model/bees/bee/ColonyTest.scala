package scala.model.bees.bee

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.genotype.Genotype
import scala.model.bees.genotype.Genotype.Genotype
import scala.utility.Point

class ColonyTest extends AnyFunSuite {
  val genotype: Genotype = Genotype()
  var bees: Set[Bee] = (1 to 100).map(_ => Bee(genotype, genotype expressInPhenotype, 0)).toSet
  var queen: Queen = Queen(None, genotype, genotype expressInPhenotype, 0, Point(10,20), null)
  val colony: Colony = queen.colony
  val colony2: Colony = Colony(queen = queen, bees = bees)


  test("Colony must have some Bees") {
    assert(colony.bees.nonEmpty)
  }

  test("The initial number of worker bees must be == of reproduction rate + 2") {
    assert(colony.bees.size == (queen.effectiveReproductionRate+2))
  }

  test("Dimension of initial colony must be proportionate at limit for cell of bee"){
    assert(colony.area*5 >= colony.numberOfBees)
  }

  test("Dimension of colony must always be proportionate at limit for cell of bee"){
    assert(colony2.area*5 >= colony2.numberOfBees)
  }

}
