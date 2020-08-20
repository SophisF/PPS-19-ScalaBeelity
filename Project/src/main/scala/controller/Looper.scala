package scala.controller

import scala.model.environment.EnvironmentManager.{addSource, evolution}
import scala.model.environment.{Environment, EnvironmentManager}
import scala.model.environment.matrix.Matrix._
import scala.model.environment.property.Variation.GenericVariation
import scala.model.environment.property.realization.TemperatureProperty
import scala.model.environment.property.source.{SeasonalPropertySource, ZonePropertySource}
import scala.model.environment.property.source.ZonePropertySource.ContinuousZonePropertySource
import scala.model.environment.property.{FilterBuilder, PropertyType}
import scala.model.environment.time.Time
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
      .foldLeft(environmentManager)((x, y) => addSource[TemperatureProperty](x, y))

    environmentManager = addSource[TemperatureProperty](environmentManager,
      SeasonalPropertySource[TemperatureProperty](TemperatureProperty.variation))

    Iterator.range(0, iterations).filter(_ % updateStep == 0).foreach(i => {

      environmentManager = evolution(environmentManager)

      if (i == iterations / 2) plot(environmentManager.environment)

      Time increment 1
    })

    plot(environmentManager.environment)
  }

  private def randomContinuousFilters(environmentWidth: Int, environmentHeight: Int, iterations: Int, quantity: Int)
  : Iterable[ContinuousZonePropertySource[TemperatureProperty]] = (0 until quantity) map (_ => {
      val values = if (Random.nextBoolean()) (50, 1) else (-50, -1)
      val filter = FilterBuilder.gaussianFunction3d(values._1, values._2, 70, 70)

      ZonePropertySource(
        Random.nextInt(environmentWidth), Random.nextInt(environmentHeight),
        filter.cols, filter.rows,
        0, iterations, filter.mapValues(it => GenericVariation[TemperatureProperty](it.toInt))
      )
    })

  /**
   * Plot the environment calling the view
   *
   * @param environment to plot
   */
  private def plot(environment: Environment): Unit = /*PropertyType.values.foreach(property => View.plot(
    environment.map.dropColumns(0.5).dropRows(0.5).mapValues(cell => cell(property).value.toDouble),
    0, 100,
    s"${property.toString}"
  ))*/5
}
