package scala.model

import model.bees.bee.EvolutionManager.calculateAveragePhenotype
import scala.model.bees.bee.Colony.Colony
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType._
import scala.model.bees.phenotype.Characteristic.{Characteristic, _}
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

object Statistic {
  type PropertyType = PropertyValue[Property]
  type Range = (Int, Int)

  implicit def ordering[E <: (CharacteristicTaxonomy, Characteristic#Expression)]: Ordering[(CharacteristicTaxonomy, Characteristic#Expression)] = Ordering.by(_._1)

  case class Statistic(colony: Colony) {
    def stat(): Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)] = CharacteristicTaxonomy.values.map(v => (v, getAverageOf(v)))

    def getAverageOf(characteristicTaxonomy: CharacteristicTaxonomy): Characteristic#Expression = {
      calculateAveragePhenotype(List(colony.queen) ++ colony.bees).expressionOf(characteristicTaxonomy)
    }

  }

}
