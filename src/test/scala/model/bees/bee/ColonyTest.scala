package scala.model.bees.bee

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.bee.utility.UtilityColonyCreator
import scala.model.bees.genotype.Genotype
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.{CharacteristicTaxonomy, EnvironmentInformation, Phenotype}
import scala.model.environment.adapter.Cell
import scala.utility.Point

class ColonyTest extends AnyFunSuite {
  private val genotype: Genotype = Genotype()

  private val queen: Queen = Queen(None, genotype, genotype expressInPhenotype, 0,  Point(10,20), null, EnvironmentInformation(Seq(Cell())))
  private  val colony1: Colony = queen.colony

  private val bees: Set[Bee] = (1 to 100).map(_ => Bee(genotype, genotype expressInPhenotype, 0, EnvironmentInformation(Seq(Cell())))).toSet
  private val colony2: Colony = Colony(queen = queen, bees = bees)

  test(s"Colony must have some Bees") {
    assert(colony1.bees.nonEmpty)
  }

  test(s"Colony raise IllegalArgumentException if set of bees is empty"){
    assertThrows[IllegalArgumentException](Colony(queen = queen, bees = Set.empty))
  }

  test(s"The initial number of worker bees must be equals to reproduction rate + 2") {
    assert(colony1.bees.size == (queen.effectiveReproductionRate+2))
  }

  test("Dimension of initial colony must be proportionate at limit for cell of bee"){
    assert(colony1.area*5 >= colony1.numberOfBees)
  }

  test("Dimension of a bigger colony must be proportionate at limit for cell of bee"){
    assert(colony2.area*5 >= colony2.numberOfBees)
  }

  test("The number of bees into a colony is limited by average of reproduction characteristic"){
    assert(colony1.maxBees == 100 * Phenotype.averagePhenotype(Set(queen) ++ bees)
      .expressionOf(CharacteristicTaxonomy.REPRODUCTION_RATE) )
  }

}
