package scala.model

import scala.model.statistic.StatisticColonies.Statistic
import scala.model.statistic.StatisticEnvironment.{StatisticalData, updateStats}
import scala.model.bees.bee.Colony.Colony
import scala.model.environment.Cell
import scala.utility.TypeUtilities.StatisticColonies

trait Model {

  def time(): Int = Time.now()

  def update(): Unit

  def statisticalData(): StatisticalData

  def statisticList(): StatisticColonies

  def colonies: List[Colony]

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
    _statisticalData = updateStats(ecosystem.environmentManager.environment, _statisticalData)
    Time.increment()
  }

  override def statisticalData(): StatisticalData = _statisticalData

  override def statisticList(): StatisticColonies = ecosystem.colonies.map(c => (c, Statistic(c).stat()))

  override def colonies: List[Colony] = ecosystem.colonies

  def colors: Seq[Double] = ecosystem.colonies.map(_.color)

  override def temperatureMatrix(): Array[Array[Double]] = propertyMatrix(_.temperature.numericRepresentation())

  override def humidityMatrix(): Array[Array[Double]] = propertyMatrix(_.humidity.numericRepresentation())

  override def pressureMatrix(): Array[Array[Double]] = propertyMatrix(_.pressure.numericRepresentation())

  private def propertyMatrix(strategy: Cell => Double): Array[Array[Double]] = {
    val env = ecosystem.environmentManager.environment.map
    env.data.map(strategy).sliding(env cols, env cols) toArray
  }
}
