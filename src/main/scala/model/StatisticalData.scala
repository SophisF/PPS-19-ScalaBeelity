package scala.model

import breeze.linalg.DenseMatrix

import scala.Array.empty
import scala.model.bees.bee.Colony.Colony
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType._
import scala.model.environment.{Cell, Environment}

object StatisticalData {

  case class StatisticalData(
    lastUpdate: Int,
    colonies: List[Colony] = List.empty,
    variationSeq: Seq[(PropertyValue[Property], Array[Double])] = properties().map(it => (it, empty))
  ) {
    def variationSequence(): Seq[(String, Array[Double])] = variationSeq.map(elem => (elem._1 toString, elem _2))

    //TODO: Add stats for colonies
  }

  def updateStats(environment: Environment, colonies: List[Colony], statistics: StatisticalData): StatisticalData = statistics.lastUpdate match {
    /*case time if Time.time - time > 30 =>
      val sumCell = environment.map.data.reduce((a, b) => Cell(a.temperature + b.temperature, a.humidity + b.humidity, a.pressure + b.pressure))
      StatisticalData(Time.time, colonies, statistics.variationSeq
        .map(el => (el._1, el._2.appended(toPercentage(el._1, sumCell.get(el._1) / environment.map.data.length)))
        ))*/
    case _ => StatisticalData(statistics.lastUpdate, colonies, statistics.variationSeq)
  }
}
/*

  def updateStats(environment: Environment, statistics: StatisticalData): StatisticalData = statistics.lastUpdate match {
    case time if compare(Time.now(), delay(30, time)) > 0 => StatisticalData(statistics.lastUpdate, environment.map, statistics.variationSeq)
      /*val sumCell = environment.map.data.reduce((a, b) => Cell(a.temperature + b.temperature, a.humidity + b.humidity, a.pressure + b.pressure))
      StatisticalData(Time.now(), environment.map, statistics.variationSeq
        .map(el => (el._1, el._2.appended(toPercentage(el._1, sumCell.get(el._1) / environment.map.data.length)))
        ))*/
    case _ => StatisticalData(statistics.lastUpdate, environment.map, statistics.variationSeq)
  }
}*/
