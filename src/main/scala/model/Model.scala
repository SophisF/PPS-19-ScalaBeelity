package scala.model

import scala.model.statistic.StatisticColonies.Statistic
import scala.model.statistic.StatisticEnvironment.{StatisticalEnvironment, updateStats}
import scala.model.environment.Cell
import scala.utility.SugarBowl.RichMappable
import scala.utility.TypeUtilities.StatisticColonies

trait Model {
  type Matrix = Array[Array[Double]]

  def time(): Int = Time.now()

  def update(): Unit

  def statisticalData(): StatisticalEnvironment

  def statisticList(): StatisticColonies

  def temperatureMatrix(): Matrix

  def humidityMatrix(): Matrix

  def pressureMatrix(): Matrix
}

class ModelImpl(numColonies: Int, updateTime: Int, dimension: (Int, Int)) extends Model {
  Time.reset()
  Time.incrementValue = updateTime
  private var _statisticalData: StatisticalEnvironment = StatisticalEnvironment()
  private var ecosystem = Ecosystem(numColonies, dimension _1, dimension _2)

  override def update(): Unit = {
    ecosystem = Ecosystem update ecosystem
    _statisticalData = updateStats(ecosystem.environmentManager.environment, _statisticalData)
    Time.increment()
  }

  override def statisticalData(): StatisticalEnvironment = _statisticalData

  override def statisticList(): StatisticColonies = ecosystem.colonies.map(c => (c, Statistic(c).stat())) toList

  override def temperatureMatrix(): Matrix = propertyMatrix(_.temperature.numericRepresentation())

  override def humidityMatrix(): Matrix = propertyMatrix(_.humidity.numericRepresentation())

  override def pressureMatrix(): Matrix = propertyMatrix(_.pressure.numericRepresentation())

  private def propertyMatrix(strategy: Cell => Double): Matrix =
    ecosystem.environmentManager.environment ~> (e => e.cells.toArray.map(strategy).sliding(e width, e width) toArray)
}
