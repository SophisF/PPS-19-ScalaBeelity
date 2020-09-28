package scala.model.statistic

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.phenotype.Characteristic.Characteristic
import scala.model.bees.phenotype.{CharacteristicTaxonomy, Phenotype}
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType.PropertyValue

object StatisticColonies {
  type PropertyType = PropertyValue[Property]
  type Range = (Int, Int)

  implicit def ordering[E <: (CharacteristicTaxonomy, Characteristic#Expression)]: Ordering[(CharacteristicTaxonomy, Characteristic#Expression)] = Ordering.by(_._1)

  case class Statistic(colony: Colony) {
    def stat(): Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)] = CharacteristicTaxonomy.values.map(v => (v, getAverageOf(v)))

    def getAverageOf(characteristicTaxonomy: CharacteristicTaxonomy): Characteristic#Expression = {
      Phenotype averagePhenotype(Set(colony queen) ++ (colony bees)) expressionOf characteristicTaxonomy
    }

  }

}
