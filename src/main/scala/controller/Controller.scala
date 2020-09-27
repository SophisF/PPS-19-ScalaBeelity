package scala.controller

import scala.controller.Looper.loop
import scala.model.{ModelImpl, Time}
import scala.utility.TypeUtilities.StatisticColonies
import scala.view.View.simulationView
import scala.model.environment.property.PropertyType.{Humidity, Pressure, Temperature}
import scala.model.statistic.StatisticEnvironment.StatisticalEnvironment

/**
 * MVC's Controller whose here act as a proxy to the model
 *
 * @param coloniesCount number of initial colonies in the simulation
 * @param timeGranularity specify the temporal gap between each update/step of the simulation
 * @param iterations number of iteration of the simulation
 * @param dimension of the environment
 */
class Controller(coloniesCount: Int, timeGranularity: Int, iterations: Int, dimension: (Int, Int)) {
  private val model = new ModelImpl(coloniesCount, timeGranularity, dimension)
  private val view = simulationView(this)

  view.createAndShow()

  /** Start the simulation */
  def start(): Unit = loop(iterations)(model.update, view.update)

  /**
   * Get the map of all the properties in the system environment
   *
   * @return the Map of the maps for each property, indicized by their name
   */
  def properties: Map[String, Array[Array[Double]]] = Map(
    Temperature.toString -> model.temperatureMatrix(),
    Humidity.toString -> model.humidityMatrix(),
    Pressure.toString -> model.pressureMatrix()
  )

  /**
   * Get the size of the environment in the simulation
   *
   * @return the size of the environment
   */
  def environmentSize: (Int, Int) = (dimension _1, dimension _2)

  /**
   * Get information about the environment during the simulation
   *
   * @return information about the environment
   */
  def statisticEnvironment: StatisticalEnvironment = model.statisticalData()

  /**
   * Get information about the colonies in the simulation
   *
   * @return information about the colonies
   */
  def statisticColonies: StatisticColonies = model.statisticList()

  /**
   * Get days elapsed since simulation started
   *
   * @return days from simulation start
   */
  def dayTime: Int = Time.dayTime()
}