package model

import breeze.linalg.DenseMatrix

import scala.model.Time
import scala.model.environment.Cell
import scala.model.environment.EnvironmentManager.EnvironmentManager
import scala.model.environment.property.Property.Property

object StatisticalData {

  case class StatisticalData(environment: DenseMatrix[Cell], variationSeq: Seq[(Property, Array[Double])], lastUpdate: Int) {

    def variationSequence(): Seq[(String, Array[Double])] = variationSeq.map(elem => (elem._1.toString, elem._2))

    def temperatureMatrix(): Array[Array[Double]] = propertyMatrix(_.temperature.toDouble)

    def humidityMatrix(): Array[Array[Double]] = propertyMatrix(_.humidity.toDouble)

    def pressureMatrix(): Array[Array[Double]] = propertyMatrix(_.pressure.toDouble)

    private def propertyMatrix(strategy: Cell => Double): Array[Array[Double]] =
      environment.data.map(strategy).sliding(environment.cols, environment.cols).toArray
  }


  def updateStats(manager: EnvironmentManager, statisticalData: StatisticalData): StatisticalData = statisticalData.lastUpdate match {
    case _ if (Time.time - statisticalData.lastUpdate > 30) => {
      val sumCell = manager.environment.map.data
        .reduce[Cell]((a, b) => Cell(a.temperature + b.temperature, a.humidity + b.humidity, a.pressure + b.pressure))
      val variationSeq = statisticalData.variationSeq.map(el => (el._1, el._2.appended((sumCell.get(el._1) / manager.environment.map.data.size).toDouble)))
      StatisticalData(manager.environment.map, variationSeq, Time.time)
    }
    case _ => StatisticalData(manager.environment.map, statisticalData.variationSeq, statisticalData.lastUpdate)
  }
}
