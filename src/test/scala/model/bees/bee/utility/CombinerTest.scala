package scala.model.bees.bee.utility

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.genotype.{Gene, GeneTaxonomy, Genotype}
import scala.model.bees.phenotype.{CharacteristicTaxonomy, Phenotype}
import scala.utility.Point

class CombinerTest extends AnyFunSuite {
  private val genotype: Genotype = Genotype(
    Gene(GeneTaxonomy.AGGRESSION_GENE, 0),
    Gene(GeneTaxonomy.GROWTH_GENE, 0))

  private val mediumAggressiveGenotype: Genotype = Genotype(
    Gene(GeneTaxonomy.TEMPERATURE_GENE, 100),
    Gene(GeneTaxonomy.PRESSURE_GENE, 100),
    Gene(GeneTaxonomy.HUMIDITY_GENE, 100),
    Gene(GeneTaxonomy.AGGRESSION_GENE, 50),
    Gene(GeneTaxonomy.GROWTH_GENE, 50))

  println(mediumAggressiveGenotype.expressInPhenotype.expressionOf(CharacteristicTaxonomy.AGGRESSION_RATE))

  private val colony1 = UtilityColonyCreator.createColony(genotype, Point(3, 3))
  private val colony2 = UtilityColonyCreator.createColony(genotype, Point(3, 3))
  private val colony3 = UtilityColonyCreator.createColony(genotype, Point(6, 6))

  private val mediumAggressiveColony = UtilityColonyCreator.createColony(mediumAggressiveGenotype, Point(3, 3))

  println(Phenotype.averagePhenotype(mediumAggressiveColony bees).expressionOf(CharacteristicTaxonomy.AGGRESSION_RATE))
  test("Two colonies that collides and have an aggression lower than a threshold should merge in only one colonies"){
    val colonies = List(colony1, colony2)

    assert(Combiner.combine(colonies, colonies).size == 1)
  }

  test("Two colonies that collides and don't have an aggression lower than a threshold should not merge in only one colonies"){
    val colonies = List(colony1, mediumAggressiveColony)

    assert(Combiner.combine(colonies, colonies).size == 2)
  }

  test("By the merging of two colonies, a new colony will be create, with the sum of the bees of the two initial colonies"){
    val colonies = List(colony1, colony2)

    assert(Combiner.combine(colonies, colonies).flatMap(_.bees).size == colonies.flatMap(_.bees).size)
  }

  test("By the merging of two colonies, a new colony will be create, with the queen of the colony with greater area"){
    val colonies = List(colony1, colony2)

    val colonyWithMaxArea = if(colony1.area > colony2.area) colony1 else colony2

    assert(Combiner.combine(colonies, colonies).map(_.queen).head.equals(colonyWithMaxArea.queen))
  }

  test("If two colonies does not collide, they should not merge each other"){
    val colonies = List(colony1, colony3)

    assert(Combiner.combine(colonies, colonies).size == colonies.size)
  }

}
