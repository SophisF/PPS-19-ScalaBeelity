package scala.utility

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.phenotype.Characteristic.Characteristic
import scala.model.bees.phenotype.CharacteristicTaxonomy

object TypeUtilities {

  type StatisticColony = List[(Colony, Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)])]


}
