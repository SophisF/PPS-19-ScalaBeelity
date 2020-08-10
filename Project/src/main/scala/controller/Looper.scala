package scala.controller

import scala.model.EnvironmentManager.{addSource, evolution}
import scala.model._
import scala.model.matrix.Matrix._
import scala.model.property.Property.{Temperature, range}
import scala.model.property.PropertyVariation.Variation
import scala.model.property.{FilterBuilder, Property, PropertySource}
import scala.model.time.Time
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

    val values = if (Random.nextBoolean()) (50, 1) else (-50, -1)
    val filter = FilterBuilder.threeDimensionalPositiveDescent(values._1, values._2, 70, 70)// TODO
    println(filter)
    val continuousFilter = PropertySource(
      Random.nextInt(environmentSize._1), Random.nextInt(environmentSize._2),
      filter.cols, filter.rows,
      /*i + updateStep*/0, 100, filter.mapValues(it => Variation(Temperature, it.toInt))
    )
    environmentManager = addSource(environmentManager, continuousFilter)

    Iterator.range(0, iterations)/*.filter(_ % updateStep == 0)*/.foreach(i => {

      environmentManager = evolution(environmentManager)

      if (i == iterations / 2) {
        plot(environmentManager.environment)
        println(environmentManager.continuousFilters.appended(continuousFilter).mkString("Array(", ", ", ")"))
        println(environmentManager.continuousFilters.mkString("Array(", ", ", ")"))
        println(environmentManager.environment.map.data.filter(it => it.temperature != range(Temperature).default).mkString("Array(", ", ", ")"))
      }
      //if (i / updateStep == iterations / (2 * updateStep)) plot(environmentManager.environment)

      Time increment 1
    })

    plot(environmentManager.environment)
  }

  /**
   * Plot the environment calling the view
   *
   * @param environment to plot
   */
  private def plot(environment: Environment): Unit = Property.values.foreach(property => View.plot(environment.map
      .dropColumns(0.3).dropRows(0.3).mapValues(cell => Property.toPercentage(property, cell.get(property)).toDouble)))
}
