package scala.model.bees.bee.utility

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.genotype.{Gene, GeneTaxonomy, Genotype}
import scala.utility.Point

class CleanerTest extends AnyFunSuite {

  private val genotype: Genotype = Genotype(
    Gene(GeneTaxonomy TEMPERATURE_GENE, 100),
    Gene(GeneTaxonomy HUMIDITY_GENE, 100),
    Gene(GeneTaxonomy PRESSURE_GENE, 100),
    Gene(GeneTaxonomy AGGRESSION_GENE, 100),
    Gene(GeneTaxonomy GROWTH_GENE, 100))

  private val lowAggressionGenotype = Genotype(
    Gene(GeneTaxonomy AGGRESSION_GENE, 0),
    Gene(GeneTaxonomy GROWTH_GENE, 0))

  private val colony1 = UtilityColonyBuilder createColony(genotype, Point(3, 3))
  private val colony2 = UtilityColonyBuilder createColony(genotype, Point(3, 3))
  private val colony3 = UtilityColonyBuilder createColony(genotype, Point(6, 6))

  private val lowAggressionColony = UtilityColonyBuilder createColony(lowAggressionGenotype, Point(3, 3))

  test("Two colonies that collides and have an aggression greater than a threshold should attack each other") {
    val colonies = List(colony1, colony2)

    assert(((Cleaner clean(colonies, colonies) flatMap (_ bees)) size) < (colonies flatMap(_ bees) size))
  }

  test("A colony with aggression lower than a threshold should not kill other colonies' bees") {
    val colonies = List(lowAggressionColony, lowAggressionColony)

    assert(((Cleaner clean(colonies, colonies) flatMap(_ bees)) size) == (colonies flatMap(_ bees) size))
  }

  test("If two colonies does not collide, they don't attack each other") {
    val colonies = List(colony1, colony3)

    assert(((Cleaner clean(colonies, colonies) flatMap(_ bees)) size) == (colonies flatMap(_ bees) size))
  }

}
