package scala.model

import scala.model.statistic.StatisticColonies.Statistic
import scala.model.statistic.StatisticEnvironment.{StatisticalEnvironment, updateStats}
import scala.model.environment.adapter.Cell
import scala.utility.SugarBowl.RichMappable
import scala.utility.TypeUtilities.StatisticColonies

/**
 * MVC's model of the application.
 */
trait Model {

  /**
   * Type of the graphics' matrix.
   */
  type Matrix = Array[Array[Double]]

  /**
   * Accessor method for the simulation's time.
   *
   * @return the time of the simulation.
   */
  def time(): Int = Time.dayTime()

  /**
   * Method to update the model.
   */
  def update(): Unit

  /**
   * Accessor method for the statistics of the environment.
   *
   * @return the environment's statistics.
   */
  def statisticalEnvironmentData(): StatisticalEnvironment

  /**
   * Accessor method for the statistics of the colonies.
   *
   * @return the colonies' statistics.
   */
  def statisticColoniesData(): StatisticColonies

  /**
   * Accessor method for the temperature.
   *
   * @param inPercentage boolean flag to remap value in percentage.
   * @return a new graphics' matrix.
   */
  def temperatureMatrix(inPercentage: Boolean = true): Matrix

  /**
   * Accessor method for the humidity.
   *
   * @param inPercentage boolean flag to remap value in percentage.
   * @return a new graphics' matrix.
   */
  def humidityMatrix(inPercentage: Boolean = true): Matrix

  /**
   * Accessor method for the pressure.
   *
   * @param inPercentage boolean flag to remap value in percentage.
   * @return a new graphics' matrix.
   */
  def pressureMatrix(inPercentage: Boolean = true): Matrix
}

object Model {

  /**
   * Concrete implementation of the model.
   *
   * @param numColonies the initial number of colonies.
   * @param updateTime  the temporal granularity of the simulation.
   * @param dimension   the dimension of the environment.
   */
  class ModelImpl(numColonies: Int, updateTime: Int, dimension: (Int, Int)) extends Model {
    Time.reset()
    Time.incrementValue = updateTime
    private var _statisticalEnvironmentData: StatisticalEnvironment = StatisticalEnvironment()
    private var ecosystem = Ecosystem(numColonies, dimension _1, dimension _2)

    override def update(): Unit = {
      ecosystem = Ecosystem update ecosystem
      _statisticalEnvironmentData = updateStats(ecosystem.environmentManager, _statisticalEnvironmentData)
      Time.increment()
    }

    override def statisticalEnvironmentData(): StatisticalEnvironment = _statisticalEnvironmentData

    override def statisticColoniesData(): StatisticColonies = ecosystem.colonies.map(c => (c, Statistic(c).statistic())) toList

    override def temperatureMatrix(inPercentage: Boolean): Matrix = propertyMatrix(_ temperature inPercentage)

    override def humidityMatrix(inPercentage: Boolean): Matrix = propertyMatrix(_ humidity inPercentage)

    override def pressureMatrix(inPercentage: Boolean): Matrix = propertyMatrix(_ pressure inPercentage)

    /**
     * Method that returns a specific matrix of the environment characteristic.
     *
     * @param strategy a strategy that defines which property matrix return.
     * @return the property matrix.
     */
    private def propertyMatrix(strategy: Cell => Double): Matrix = ecosystem.environmentManager ~>
      (environment => environment.cells().data.map(strategy).sliding(environment width, environment width) toArray)
  }
}
