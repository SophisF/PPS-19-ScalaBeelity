package scala.model.statistic

import scala.model.Time
import scala.model.environment.EnvironmentManager
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType.{PropertyValue, propertiesType}
import scala.Iterable.empty
import scala.model.Time.now
import scala.utility.Conversion.{mapOf, sequenceOf}
import scala.utility.SugarBowl.{RichMap, RichMappable, RichOptional}

/** Store statistic for environment */
private[model] object StatisticEnvironmentData {
  type PropertyType = PropertyValue[Property]

  case class StatisticEnvironmentData(
    averageProperties: Map[PropertyType, Iterable[Double]] = propertiesType.map((_, empty[Double])),
    lastUpdate: Time = now()
  ) {
    def variationSequence(): Seq[(String, Iterable[Double])] = averageProperties.map(e => (e._1 toString, e _2))
  }

  /**
   * Update statistics with new data.
   *
   * @param environment, the environment
   * @param statistics to update
   *
   * @return new statistic data.
   */
  def updateStats(environment: EnvironmentManager, statistics: StatisticEnvironmentData): StatisticEnvironmentData =
    statistics.lastUpdate match {
      case time if time.year < now().year || time.month < now().month => environment.cells().data
        .foldLeft(Map.empty[PropertyType, Double])((propertyTrend, cell) => propertiesType
          .map(property => (property, (propertyTrend ? property ! .0) + cell(property, percentage = true)))). ~> (sum =>
        StatisticEnvironmentData(propertiesType.map(property => (property, (statistics.averageProperties ? property
          ! empty).appended(sum(property) / environment.cells().size)))))
      case _ => StatisticEnvironmentData(statistics averageProperties, statistics lastUpdate)
    }
}