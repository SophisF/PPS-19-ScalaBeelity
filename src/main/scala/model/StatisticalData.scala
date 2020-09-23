package scala.model

import model.bees.bee.EvolutionManager.calculateAveragePhenotype

import scala.Iterable.empty
import scala.model.bees.bee.Colony.Colony
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType._
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
