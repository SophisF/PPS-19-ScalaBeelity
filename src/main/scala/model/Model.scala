package scala.model

import scala.model.statistic.StatisticColonies.Statistic
import scala.model.statistic.StatisticEnvironment.{StatisticalEnvironment, updateStats}
import scala.model.environment.adapter.Cell
import scala.utility.SugarBowl.RichMappable
import scala.utility.TypeUtilities.StatisticColonies

trait Model {
  type Matrix = Array[Array[Double]]

  def time(): Int = Time.dayTime()

  def update(): Unit

  def statisticalData(): StatisticalEnvironment

  def statisticList(): StatisticColonies

  def temperatureMatrix(inPercentage: Boolean = true): Matrix

  def humidityMatrix(inPercentage: Boolean = true): Matrix

  def pressureMatrix(inPercentage: Boolean = true): Matrix
}

object Model {

  class ModelImpl(numColonies: Int, updateTime: Int, dimension: (Int, Int)) extends Model {
    Time.reset()
    Time.incrementValue = updateTime
    private var _statisticalData: StatisticalEnvironment = StatisticalEnvironment()
    private var ecosystem = Ecosystem(numColonies, dimension _1, dimension _2)

    override def update(): Unit = {
      ecosystem = Ecosystem update ecosystem
      _statisticalData = updateStats(ecosystem.environmentManager, _statisticalData)
      Time.increment()
    }

    override def statisticalData(): StatisticalEnvironment = _statisticalData

    override def statisticList(): StatisticColonies = ecosystem.colonies.map(c => (c, Statistic(c).statistic())) toList

    override def temperatureMatrix(inPercentage: Boolean): Matrix = propertyMatrix(_ temperature inPercentage)

    override def humidityMatrix(inPercentage: Boolean): Matrix = propertyMatrix(_ humidity inPercentage)

    override def pressureMatrix(inPercentage: Boolean): Matrix = propertyMatrix(_ pressure inPercentage)

    private def propertyMatrix(strategy: Cell => Double): Matrix = ecosystem.environmentManager ~>
      (environment => environment.cells().data.map(strategy).sliding(environment width, environment width) toArray)
  }
}
