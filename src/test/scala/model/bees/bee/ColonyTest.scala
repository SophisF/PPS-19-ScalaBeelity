package scala.model.bees.bee

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.genotype.{Genotype, Influence}
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.{CharacteristicTaxonomy, Phenotype}
import scala.utility.Point

class ColonyTest extends AnyFunSuite {
  val genotype: Genotype = Genotype()
  val queen: Queen = Queen(None, genotype, genotype expressInPhenotype, 0, Point(10,20), null)
  val colony: Colony = queen.colony
  val bees: Set[Bee] = (1 to 100).map(_ => Bee(genotype, genotype expressInPhenotype, 0)).toSet
  val colony2: Colony = Colony(queen = queen, bees = bees)
  val bees3: Set[Bee] = (1 to 1000).map(_ => Bee(genotype, genotype expressInPhenotype, 0)).toSet
  val colony3: Colony = Colony(queen = queen, bees = bees)


  test(s"Colony must have some Bees") {
    assert(colony.bees.nonEmpty)
  }

  test(s"Colony raise IllegalArgumentException if set of bees is empty"){
    assertThrows[IllegalArgumentException](Colony(queen = queen, bees = Set.empty))
  }

  test(s"The initial number of worker bees must be equals to reproduction rate + 2") {
    assert(colony.bees.size == (queen.effectiveReproductionRate+2))
  }

  test("Dimension of initial colony must be proportionate at limit for cell of bee"){
    assert(colony.area*5 >= colony.numberOfBees)
  }

  test("Dimension of colony must always be proportionate at limit for cell of bee"){
    assert(colony2.area*5 >= colony2.numberOfBees)
  }

  test("The number of bees into a colony is limited by average of reproduction characteristic"){
    assert(colony.maxBees == 100 * Phenotype.averagePhenotype(Set(queen) ++ bees)
      .expressionOf(CharacteristicTaxonomy.REPRODUCTION_RATE) )
  }

  test("The number of bees into a colony must be limited"){
    assert(colony3.numberOfBees <= 100 * Phenotype.averagePhenotype(Set(queen) ++ bees)
      .expressionOf(CharacteristicTaxonomy.REPRODUCTION_RATE) )
  }


}
