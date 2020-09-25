package scala.model.bees.bee.utility

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.{Bee, Colony, Queen}
import scala.model.bees.genotype.Genotype.Genotype
import scala.util.Random
import scala.utility.Point

object UtilityColonyBuilder {

  private val age = 0
  private val t = 36
  private val p = 1050
  private val h = 100

  def createColony(genotype: Genotype, position: Point): Colony = {
    Colony(Random.nextDouble(), Queen(None, genotype, genotype expressInPhenotype, age, t, p, h, position, null),
      (1 to 10).map(_ => Bee(genotype, genotype expressInPhenotype, age, t, p, h)).toSet)
  }


}
