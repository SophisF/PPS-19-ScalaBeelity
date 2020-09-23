package scala.controller

import scala.model.ModelImpl
import scala.model.bees.bee.Colony.Colony
import scala.model.environment.property.PropertyType.{Humidity, Pressure, Temperature}
import scala.model.statistic.StatisticEnvironment.StatisticalEnvironment
import scala.utility.TypeUtilities.StatisticColony
import scala.view.View.ViewImpl

class Controller(numColonies: Int, updateTime: Int, iterations: Int, dimension: Int) {

  private val view = new ViewImpl(this)
  private val model = new ModelImpl(numColonies, updateTime, dimension)

  view.createSimulationGUI()

  def start(): Unit = Looper.loop(iterations)(model.update)(view.update)

  def properties: Map[String, Array[Array[Double]]] = Map(
    Temperature.toString -> model.temperatureMatrix(),
    Humidity.toString -> model.humidityMatrix(),
    Pressure.toString -> model.pressureMatrix()
  )

  def environmentDimension(): (Int, Int) = (dimension, dimension)

  def colonies: Seq[Colony] = model.colonies

  def statisticEnvironment: StatisticalEnvironment = model.statisticalData()

  def statisticColonies() : StatisticColony = model.statisticList()
}