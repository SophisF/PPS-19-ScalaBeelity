package scala.controller

import scala.model.ModelImpl
import scala.model.statistic.StatisticColonies.Statistic
import scala.model.statistic.StatisticEnvironment.StatisticalData
import scala.model.bees.bee.Colony
import scala.model.bees.bee.Colony.Colony
import scala.model.bees.phenotype.Characteristic.Characteristic
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.environment.property.PropertyType.{Humidity, Pressure, Temperature}
import scala.utility.Point
import scala.utility.TypeUtilities.StatisticColony
import scala.view.View.ViewImpl

class Controller(numColonies: Int, updateTime: Int, iterations: Int, dimension: Int) {

  private val view = new ViewImpl(this)
  private val model = new ModelImpl(numColonies, updateTime, dimension)

  view.createSimulationGUI()

  def start(): Unit = Looper.loop(iterations)(model.update)(view.update)


  def playSimulation() = ???

  def pauseSimulation() = ???

  def accelerateSimulation() = ???

  def properties: Map[String, Array[Array[Double]]] = Map(
    Temperature.toString -> model.temperatureMatrix(),
    Humidity.toString -> model.humidityMatrix(),
    Pressure.toString -> model.pressureMatrix()
  )

  def environmentDimension(): (Int, Int) = (dimension, dimension)

  def color: Seq[Double] = model.colors

  def colonies: Seq[Colony] = model.colonies

  def statisticalData: StatisticalData = model.statisticalData()

  def statistic() : StatisticColony = model.statisticList()
}