package scala.controller

import scala.annotation.tailrec
import scala.model.environment.EnvironmentManager.{addSource, evolution}
import scala.model.environment.matrix.Matrix.DroppableMatrix
import scala.model.environment.{Environment, EnvironmentManager, GeneratorClimateChange}
import scala.model.environment.property.Variation.GenericVariation
import scala.model.environment.property.realization.TemperatureProperty
import scala.model.environment.property.source.ContinuousSource
import scala.model.environment.property.{FilterBuilder, PropertyType}
import scala.model.environment.property.source.ContinuousSource.ContinuousSourceImpl
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

    /*var t = new Timer()
    environmentManager = randomContinuousFilters(environmentSize._1, environmentSize._2, iterations, 5)
      .foldLeft(environmentManager)(addSource)
    println("Time to build continuous filters: " + t.elapsedTime())

    t = new Timer()
    environmentManager = addSource(environmentManager, SeasonalSource[TemperatureProperty](TemperatureProperty.variation))
    println("Time to build seasonal filter: " + t.elapsedTime())

    t = new Timer()
    Iterator.range(0, iterations).filter(_ % updateStep == 0).foreach(i => {

      //t = new Timer()
      environmentManager = evolution(environmentManager)
      //println("Time for an evolution: " + t.elapsedTime())

      //if (i == iterations / 2) plot(environmentManager.environment)

      Time increment updateStep
    })
    println("Time to run: " + t.elapsedTime())*/

    environmentManager = GeneratorClimateChange.generateClimate(environmentSize._1, environmentSize._2, iterations)
      .foldLeft(environmentManager)(addSource)

    //environmentManager = GeneratorClimateChange.generateSeason().foldLeft(environmentManager)(addSource)

    //    Iterator.range(0, iterations).filter(_ % updateStep == 0).foreach(i => {
    //
    //      environmentManager = evolution(environmentManager)
    //
    //      if (i == iterations / 2) plot(environmentManager.environment)
    //
    //      Time increment 1
    //    })

    //TODO: Da sostituire con Ecosystem
    @tailrec
    def loop(environment: EnvironmentManager.EnvironmentManager, iterations: Int): EnvironmentManager.EnvironmentManager = iterations match {
      case 0 => environment
      case _ => {
        val env = evolution(environment)
        //if (iterations % 100 == 0) plot(env.environment)
        //println(iterations)
        Time.increment()
        //colonies.update(time, env)
        loop(env, iterations - 1)
      }
    }

    loop(environmentManager, iterations)

    plot(environmentManager.environment)
  }

  private def randomContinuousFilters(environmentWidth: Int, environmentHeight: Int, iterations: Int, quantity: Int)
  : Iterable[ContinuousSource[TemperatureProperty]] = (0 until quantity) map (_ => {
      val values = if (Random.nextBoolean()) (50, 1) else (-50, -1)
      val filter = FilterBuilder.gaussianFunction3d(values._1, values._2, 70, 70)

      ContinuousSourceImpl[TemperatureProperty](filter.mapValues(it => GenericVariation[TemperatureProperty](it.toInt)),
        (value, percentage) => value * percentage / 100, Random.nextInt(environmentWidth),
        Random.nextInt(environmentHeight), filter.cols, filter.rows, 0, iterations)
    })

  /**
   * Plot the environment calling the view
   *
   * @param environment to plot
   */
  private def plot(environment: Environment): Unit = PropertyType.values.foreach(property => View.plot(
    environment.map.dropColumns(0.5).dropRows(0.5).mapValues(cell => cell(property).asNumericPercentage().toDouble),
    s"${property.toString}"
  ))
}
