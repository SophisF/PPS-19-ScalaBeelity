package scala.controller

import scala.model.environment.EnvironmentManager.{addSource, evolution}
import scala.model.{Time, _}
import scala.model.environment.{Environment, EnvironmentManager}
import scala.model.environment.matrix.Matrix._
import scala.model.environment.property.Property.{Temperature, toPercentage}
import scala.model.environment.property.PropertySource.SeasonalPropertySource
import scala.model.environment.property.ZonePropertySource.ContinuousZonePropertySource
import scala.model.environment.property.PropertyVariation.Variation
import scala.model.environment.property.{FilterBuilder, Property, ZonePropertySource}
import scala.util.Random
import scala.view.View

/**
 * Simply controller of the test
 *
 * @author Paolo Baldini
 */
object Looper {

  /**
   * Run a simulation
   *
   * @param environmentSize size of the map
   * @param iterations number of iterations to simulate
   * @param updateStep how often update the environment
   */
  def run(environmentSize: (Int, Int), iterations: Int, updateStep: Int): Unit = {
    var environmentManager = EnvironmentManager(environmentSize._1, environmentSize._2)

    plot(environmentManager.environment)

    environmentManager = randomContinuousFilters(environmentSize._1, environmentSize._2, iterations, 5)
      .foldLeft(environmentManager)(addSource)

    environmentManager = addSource(environmentManager, SeasonalPropertySource(Property.Humidity))

    Iterator.range(0, iterations).filter(_ % updateStep == 0).foreach(i => {

      environmentManager = evolution(environmentManager)

      if (i == iterations / 2) plot(environmentManager.environment)

      Time increment 1
    })

    plot(environmentManager.environment)
  }

  private def randomContinuousFilters(environmentWidth: Int, environmentHeight: Int, iterations: Int, quantity: Int)
  : Iterable[ContinuousZonePropertySource] = (0 until quantity) map (_ => {
      val values = if (Random.nextBoolean()) (50, 1) else (-50, -1)
      val filter = FilterBuilder.gaussianFunction3d(values._1, values._2, 70, 70)

      ZonePropertySource(
        Random.nextInt(environmentWidth), Random.nextInt(environmentHeight),
        filter.cols, filter.rows,
        0, iterations, filter.mapValues(it => Variation(Temperature, it.toInt))
      )
    })

  /**
   * Plot the environment calling the view
   *
   * @param environment to plot
   */
  private def plot(environment: Environment): Unit = Property.values.foreach(property => View.plot(
    environment.map.dropColumns(0.5).dropRows(0.5).mapValues(c => toPercentage(property, c get property) toDouble),
    Property.range(property).minValue,
    Property.range(property).maxValue,
    s"${property.toString} (${Property.range(property).minValue}, ${Property.range(property).maxValue})"
  ))
}
