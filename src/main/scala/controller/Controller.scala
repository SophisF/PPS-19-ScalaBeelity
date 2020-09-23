package scala.controller

import scala.controller.Looper.loop
import scala.model.ModelImpl
import scala.model.statistic.StatisticEnvironment.StatisticalData
import scala.model.bees.bee.Colony.Colony
import scala.model.environment.property.PropertyType.{Humidity, Pressure, Temperature}
import scala.utility.TypeUtilities.StatisticColonies
import scala.view.View.simulationView

class Controller(numColonies: Int, updateTime: Int, iterations: Int, dimension: (Int, Int)) {

  private val model = new ModelImpl(numColonies, updateTime, dimension._1)
  private val view = simulationView(this)
  view.createAndShow()

  def start(): Unit = loop(iterations)(model.update)(view.update)

  def playSimulation() = ???

  def pauseSimulation() = ???

  def accelerateSimulation() = ???

  def properties: Map[String, Array[Array[Double]]] = Map(
    Temperature.toString -> model.temperatureMatrix(),
    Humidity.toString -> model.humidityMatrix(),
    Pressure.toString -> model.pressureMatrix()
  )

  def environmentDimension(): (Int, Int) = (dimension._1, dimension._1)

  def color: Seq[Double] = model colors

  def colonies: Seq[Colony] = model colonies

  def statisticalData: StatisticalData = model.statisticalData()

  def statistic() : StatisticColonies = model.statisticList()
}