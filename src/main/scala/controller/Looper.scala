package scala.controller

import scala.annotation.tailrec
import scala.model.Time
import scala.model.environment.EnvironmentManager._
import scala.model.environment.matrix.Matrix._
import scala.model.environment.property.Property
import scala.model.environment.property.Property.toPercentage
import scala.model.environment.{Environment, EnvironmentManager}
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
   * @param iterations      number of iterations to simulate
   * @param updateStep      how often update the environment
   */
  def run(environmentSize: (Int, Int), iterations: Int, updateStep: Int): Unit = {
    var environmentManager = EnvironmentManager(environmentSize._1, environmentSize._2)

    plot(environmentManager.environment)


    environmentManager = GeneratorClimateChange.generateClimate(environmentSize._1, environmentSize._2, iterations)
      .foldLeft(environmentManager)(addSource)

    environmentManager = GeneratorClimateChange.generateSeason().foldLeft(environmentManager)(addSource)

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
    def loop(environment: EnvironmentManager, iterations: Int): EnvironmentManager = iterations match {
      case 0 => environment
      case _ => {
        val env = evolution(environment)
        if (iterations % 50 == 0) plot(env.environment)
        Time.increment()
        //colonies.update(time, env)
        loop(env, iterations - 1)
      }
    }

    loop(environmentManager, iterations)
  }


  /**
   * Plot the environment calling the view
   *
   * @param environment to plot
   */
  private def plot(environment: Environment): Unit = Property.values.foreach(property => View.plot(
    environment.map.dropColumns(0.5).dropRows(0.5).mapValues(c => toPercentage(property, c get property) toDouble),
    0,
    100,
    s"${property.toString} (${Property.range(property).minValue}, ${Property.range(property).maxValue})"
  ))
}
