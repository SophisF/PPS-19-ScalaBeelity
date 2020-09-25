package scala.model.bees.bee.utility

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.{Bee, Colony, Queen}
import scala.model.bees.genotype.Genotype.Genotype
import scala.util.Random
import scala.utility.Point

object UtilityColonyBuilder {

  private val age = 0

  def createColony(genotype: Genotype, position: Point): Colony = {
    Colony(Random.nextDouble(), Queen(None, genotype, genotype expressInPhenotype, age, position, null, 36, 1050, 80),
      (1 to 10).map(_ => Bee(genotype, genotype expressInPhenotype, age, 36, 1050, 80)).toSet)
  }


}
