package model

import scala.Array.empty
import scala.model.Time
import scala.model.bees.bee.Colony.Colony
import scala.model.environment.property.Property.{Humidity, Pressure, Property, Temperature, toPercentage}
import scala.model.environment.{Cell, Environment}

object StatisticalData {

  case class StatisticalData(
                              lastUpdate: Int,
                              colonies: List[Colony] = List.empty,
                              variationSeq: Seq[(Property, Array[Double])] = Seq((Temperature, empty), (Humidity, empty), (Pressure, empty))
                            ) {
    def variationSequence(): Seq[(String, Array[Double])] = variationSeq.map(elem => (elem._1 toString, elem _2))

    //TODO: Add stats for colonies

  }

  def updateStats(environment: Environment, colonies: List[Colony], statistics: StatisticalData): StatisticalData = statistics.lastUpdate match {
    case time if Time.time - time > 30 =>
      val sumCell = environment.map.data.reduce((a, b) => Cell(a.temperature + b.temperature, a.humidity + b.humidity, a.pressure + b.pressure))
      StatisticalData(Time.time, colonies, statistics.variationSeq
        .map(el => (el._1, el._2.appended(toPercentage(el._1, sumCell.get(el._1) / environment.map.data.length)))
        ))
    case _ => StatisticalData(statistics.lastUpdate, colonies, statistics.variationSeq)
  }
}
