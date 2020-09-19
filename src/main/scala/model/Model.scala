package scala.model

import scala.model.StatisticalData.{StatisticalData, updateStats}
import scala.model.environment.Cell
import scala.utility.Point

trait Model {
  def time(): Int

  def update(): Unit

  def statisticalData(): StatisticalData

  def colonies: List[(Point, Int)]

  def temperatureMatrix(): Array[Array[Double]]

  def humidityMatrix(): Array[Array[Double]]

  def pressureMatrix(): Array[Array[Double]]
}

class ModelImpl(numColonies: Int, updateTime: Int, dimension: Int) extends Model {


  Time.initialize()
  Time.setIncrementValue(updateTime)
  private var _statisticalData: StatisticalData = StatisticalData()
  private val ecosystem = new Ecosystem(numColonies, dimension, dimension)

  override def update(): Unit = {
    ecosystem.update()
    _statisticalData = updateStats(ecosystem.environmentManager.environment, ecosystem.colonies, _statisticalData)
    Time.increment()
  }

  override def time(): Int = Time.time

  override def statisticalData(): StatisticalData = _statisticalData

  def colors: Seq[Double] = ecosystem.colonies.map(_.color)

  override def colonies: List[(Point, Int)] = ecosystem.colonies.map(c => (c.position, c.dimension))

  override def temperatureMatrix(): Array[Array[Double]] = propertyMatrix(_.temperature.numericRepresentation())

  override def humidityMatrix(): Array[Array[Double]] = propertyMatrix(_.humidity.numericRepresentation())

  override def pressureMatrix(): Array[Array[Double]] = propertyMatrix(_.pressure.numericRepresentation())

  private def propertyMatrix(strategy: Cell => Double): Array[Array[Double]] = {
    val env = ecosystem.environmentManager.environment.map
    env.data.map(strategy).sliding(env cols, env cols) toArray
  }
}
