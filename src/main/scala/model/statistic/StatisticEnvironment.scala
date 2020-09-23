package scala.model.statistic

import scala.model.Time
import scala.model.environment.Environment
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType.{PropertyValue, properties}
import scala.Iterable.empty

object StatisticEnvironment {
  type PropertyType = PropertyValue[Property]
  type Range = (Int, Int)

  case class StatisticalData(
    averageProperties: Map[PropertyType, Iterable[Double]] = properties().map(it => (it, empty[Double])).toMap,
    lastUpdate: Time = Time.now()
  ) {
    def variationSequence(): Seq[(String, Iterable[Double])] = averageProperties.map(e => (e._1 toString, e _2)) toSeq
  }

  def updateStats(environment: Environment, statistics: StatisticalData): StatisticalData =
    statistics.lastUpdate match {
      case time if Time.elapsed(time, 30) =>
        val sum = environment.map.data.foldLeft(Map.empty[PropertyType, Double])((propertyTrend, cell) => properties()
          .map(property => (property, propertyTrend.getOrElse(property, .0) + cell(property).numericRepresentation()))
          .toMap)
        StatisticalData(properties().map(property => (property, statistics.averageProperties
          .getOrElse(property, empty).toSeq.appended(sum(property) / environment.map.data.length))).toMap)
      case _ => StatisticalData(statistics averageProperties, statistics lastUpdate)
    }
}
