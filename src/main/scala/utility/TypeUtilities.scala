package scala.utility

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.phenotype.Characteristic.Characteristic
import scala.model.bees.phenotype.CharacteristicTaxonomy

/**
 * Utilities for type in system.
 */
object TypeUtilities {

  type StatisticColony = (Colony, Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)])

  type StatisticColonies = List[StatisticColony]

  type StatisticEnvironment = Seq[(String, Iterable[Double])]

  type Range = (Int, Int)
}
