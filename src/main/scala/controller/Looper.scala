package scala.controller

import scala.model.environment.EnvironmentManager.{addSource, evolution}
import scala.model.environment.matrix.Matrix.DroppableMatrix
import scala.model.environment.{Environment, EnvironmentManager, GeneratorClimateChange}
import scala.model.environment.property.PropertyType
import scala.model.environment.time.Time
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

    environmentManager = GeneratorClimateChange.generateClimate((environmentSize._1, environmentSize._2), iterations)
      .foldLeft(environmentManager)(addSource)

    //environmentManager = GeneratorClimateChange.generateSeason().foldLeft(environmentManager)(addSource)

    Iterator.range(0, iterations).filter(_ % updateStep == 0).foreach(i => {

      environmentManager = evolution(environmentManager)

      if (i == iterations / 2) plot(environmentManager.environment)

      Time increment updateStep
    })

    //TODO: Da sostituire con Ecosystem
    /*@tailrec
    def loop(environment: EnvironmentManager.EnvironmentManager, iterations: Int): EnvironmentManager.EnvironmentManager = iterations match {
      case x if x <= 0 => environment
      case _ => {
        val env = evolution(environment)
        //if (iterations % 100 == 0) plot(env.environment)
        //println(iterations)
        Time.increment(updateStep)
        //colonies.update(time, env)
        loop(env, iterations - updateStep)
      }
    }*/

    //loop(environmentManager, iterations)

    plot(environmentManager.environment)
  }

  /**
   * Plot the environment calling the view
   *
   * @param environment to plot
   */
  private def plot(environment: Environment): Unit = PropertyType.properties.foreach(property => View.plot(
    environment.map.dropColumns(0.5).dropRows(0.5).mapValues(cell => property.helper.percentage(cell(property))),
    s"${property.toString}"
  ))
}
