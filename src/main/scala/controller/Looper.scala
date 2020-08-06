package scala.controller

import scala.model._
import scala.model.property.Filter._
import scala.model.property.Property
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
    var environment = Environment((environmentSize._1, environmentSize._2), Cell(5, 5, 5))

    plot(environment)

    Iterator.range(0, iterations).filter(_ % updateStep == 0).foreach(_ => {
      val values = if (Random.nextBoolean()) (50, 1) else (-50, -1)
      val filter = PropertySource.threeDimensionalPositiveDescent(values._1, values._2, 30, 30)// TODO

      environment = environment + Filter(filter.mapValues(it => Variation(it.toInt, Property.Temperature)),
        (Random.nextInt(environmentSize._1), Random.nextInt(environmentSize._2)), (filter.cols, filter.rows))
    })

    plot(environment)
  }

  /**
   * Plot the environment calling the view
   *
   * @param environment to plot
   */
  private def plot(environment: Environment): Unit = Property.values.foreach(property => View.plot(environment.map
    .mapValues(cell => Property.toPercentage(property, cell.get(property)).toDouble)))
}
