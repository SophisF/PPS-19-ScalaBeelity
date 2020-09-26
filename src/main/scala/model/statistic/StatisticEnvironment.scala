package scala.model.statistic

import scala.model.Time
import scala.model.environment.Environment
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType.{PropertyValue, properties}
import scala.Iterable.empty
import scala.model.Time.now
import scala.utility.Conversion.{mapOf, sequenceOf}
import scala.utility.SugarBowl.{RichMap, RichOptional}

object StatisticEnvironment {
  type PropertyType = PropertyValue[Property]

  case class StatisticalEnvironment(
    averageProperties: Map[PropertyType, Iterable[Double]] = properties().map((_, empty[Double])),
    lastUpdate: Time = now()
  ) {
    def variationSequence(): Seq[(String, Iterable[Double])] = averageProperties.map(e => (e._1 toString, e _2))
  }

  def updateStats(environment: Environment, statistics: StatisticalEnvironment): StatisticalEnvironment =
    statistics.lastUpdate match {
      case time if Time.elapsed(time, 30) =>
        val sum = environment.cells.foldLeft(Map.empty[PropertyType, Double])((propertyTrend, cell) => properties()
          .map(property => (property, (propertyTrend ? property ! .0) + cell(property).numericRepresentation())))
        StatisticalEnvironment(properties().map(property => (property, (statistics.averageProperties ? property ! empty)
          .appended(sum(property) / environment.cells.size))))
      case _ => StatisticalEnvironment(statistics averageProperties, statistics lastUpdate)
    }
}