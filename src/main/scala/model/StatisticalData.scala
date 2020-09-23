package scala.model

import scala.Iterable.empty
import scala.model.bees.bee.Colony.Colony
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType._
import scala.model.bees.phenotype.Characteristic.Characteristic
import scala.model.bees.phenotype.{CharacteristicTaxonomy, Phenotype}
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.environment.Environment
import scala.model.bees.phenotype.Characteristic._

object StatisticalData {
  type PropertyType = PropertyValue[Property]
  type Range = (Int, Int)

  case class StatisticalData(
    colonies: List[Colony] = List.empty,
    averageProperties: Map[PropertyType, Iterable[Double]] = properties().map(it => (it, empty[Double])).toMap,
    lastUpdate: Time = Time.now()
  ) {
    def variationSequence(): Seq[(String, Iterable[Double])] = averageProperties.map(e => (e._1 toString, e _2)) toSeq

    def temperatureRange: List[Range] =  this expression CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY map toTuple

    def pressureRange: List[Range] = this expression CharacteristicTaxonomy.PRESSURE_COMPATIBILITY map toTuple

    def humidityRange: List[Range] = this expression CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY map toTuple

    def averageAggression: List[Int] = this expression CharacteristicTaxonomy.AGGRESSION_RATE map toInt

    def averageReproduction: List[Int] = this expression CharacteristicTaxonomy.REPRODUCTION_RATE map toInt

    def averageSpeed: List[Int] = this expression CharacteristicTaxonomy.SPEED map toInt

    def averageLongevity: List[Int] = this expression CharacteristicTaxonomy.LONGEVITY map toInt

    private def expression(characteristicTaxonomy: CharacteristicTaxonomy): List[Characteristic#Expression] =
      colonies.map(c => Phenotype.averagePhenotype(Set(c.queen) ++ c.bees).expressionOf(characteristicTaxonomy))
  }

  def updateStats(environment: Environment, colonies: List[Colony], statistics: StatisticalData): StatisticalData =
    statistics.lastUpdate match {
      case time if Time.elapsed(time, 30) =>
        val sum = environment.map.data.foldLeft(Map.empty[PropertyType, Double])((propertyTrend, cell) => properties()
          .map(property => (property, propertyTrend.getOrElse(property, .0) + cell(property).numericRepresentation()))
          .toMap)
        StatisticalData(colonies, properties().map(property => (property, statistics.averageProperties
          .getOrElse(property, empty).toSeq.appended(sum(property) / environment.map.data.length))).toMap)
      case _ => StatisticalData(colonies, statistics averageProperties, statistics lastUpdate)
    }
}
