package model

import model.StatisticalData.{StatisticalData, updateStats}

import scala.model.environment.Cell
import scala.utility.Point
import scala.model.{Ecosystem, Time}


trait Model {
  def time(): Int

  def update(): Unit

  def statisticalData(): StatisticalData

  def colonies: List[(Point, Int, Double)]

  def temperatureMatrix(): Array[Array[Double]]

  def humidityMatrix(): Array[Array[Double]]

  def pressureMatrix(): Array[Array[Double]]
}


class ModelImpl(numColonies: Int, updateTime: Int, dimension: Int) extends Model {

  Time.initialize()
  Time.setIncrementValue(updateTime)
  private var _statisticalData: StatisticalData = StatisticalData(Time.time)
  private val ecosystem = new Ecosystem(numColonies, dimension, dimension)

  override def update(): Unit = {
    ecosystem.update()
    _statisticalData = updateStats(ecosystem.environmentManager.environment, ecosystem.colonies, _statisticalData)
    Time.increment()
  }

  override def time(): Int = Time.time

  override def statisticalData(): StatisticalData = _statisticalData

  override def colonies: List[(Point, Int, Double)] = ecosystem.colonies.map(c => (c.position, c.dimension, c.color))

  override def temperatureMatrix(): Array[Array[Double]] = propertyMatrix(_.temperature.toDouble)

  override def humidityMatrix(): Array[Array[Double]] = propertyMatrix(_.humidity.toDouble)

  override def pressureMatrix(): Array[Array[Double]] = propertyMatrix(_.pressure.toDouble)

  private def propertyMatrix(strategy: Cell => Double): Array[Array[Double]] = {
    val env = ecosystem.environmentManager.environment.map
    env.data.map(strategy).sliding(env cols, env cols) toArray
  }
}
