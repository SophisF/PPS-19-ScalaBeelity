package scala.model.statistic

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.phenotype.Characteristic.Characteristic
import scala.model.bees.phenotype.{CharacteristicTaxonomy, Phenotype}
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType.PropertyValue

/**
 * Object that represents statistic for colonies.
 */
object StatisticColonies {

  type PropertyType = PropertyValue[Property]

  /**
   * Implicit method that represents a strategy for sorting instances of a type.
   * @tparam E is a type to sort.
   * @return the elements order by.
   */
  implicit def ordering[E <: (CharacteristicTaxonomy, Characteristic#Expression)]: Ordering[(CharacteristicTaxonomy, Characteristic#Expression)] = Ordering.by(_._1)

  /**
   * Case class for colony's statistic .
   * @param colony the colony to calculate its statistic.
   */
  case class Statistic(colony: Colony) {
    def statistic(): Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)] = CharacteristicTaxonomy.values.map(v => (v, getAverageOf(v)))

    private def getAverageOf(characteristicTaxonomy: CharacteristicTaxonomy): Characteristic#Expression =
      Phenotype averagePhenotype (Set(colony queen) ++ (colony bees)) expressionOf characteristicTaxonomy
  }

}
