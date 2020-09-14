package model

import breeze.linalg.DenseMatrix

import scala.Array.empty
import scala.model.Time
import scala.model.environment.Cell.operation
import scala.model.environment.{Cell, Environment}
import scala.model.environment.property.Property.{Humidity, Pressure, Property, Temperature}

object StatisticalData {

  case class StatisticalData(
    lastUpdate: Int,
    environment: DenseMatrix[Cell],
    variationSeq: Seq[(Property, Array[Double])] = Seq((Temperature, empty), (Humidity, empty), (Pressure, empty))
  ) {
    def variationSequence(): Seq[(String, Array[Double])] = variationSeq.map(elem => (elem._1 toString, elem _2))

    def temperatureMatrix(): Array[Array[Double]] = propertyMatrix(_.temperature.toDouble)

    def humidityMatrix(): Array[Array[Double]] = propertyMatrix(_.humidity.toDouble)

    def pressureMatrix(): Array[Array[Double]] = propertyMatrix(_.pressure.toDouble)

    private def propertyMatrix(strategy: Cell => Double): Array[Array[Double]] =
      environment.data.map(strategy).sliding(environment cols, environment cols) toArray
  }

  def updateStats(environment: Environment, statistics: StatisticalData): StatisticalData = statistics.lastUpdate match {
    case time if Time.time - time > 30 =>
      val sumCell = environment.map.data.reduce((a, b) => Cell(a.temperature + b.temperature, a.humidity + b.humidity, a.pressure + b.pressure))
      StatisticalData(Time.time, environment.map, statistics.variationSeq.map(el => el._1 match {
        case Pressure => (el._1, el._2.appended(sumCell.get(el._1) / 100 / environment.map.data.length.toDouble))
        case _ => (el._1, el._2.appended(sumCell.get(el._1) / environment.map.data.length.toDouble))
      }))
    case _ => StatisticalData(statistics.lastUpdate, environment.map, statistics.variationSeq)
  }
}
