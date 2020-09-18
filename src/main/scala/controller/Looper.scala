package scala.controller

import scala.annotation.tailrec

/**
 * Simply controller of the test
 *
 * @author Paolo Baldini
 */
object Looper {

  /*
    /**
     * Run a simulation
     *
     * @param environmentSize size of the map
     * @param iterations      number of iterations to simulate
     * @param updateStep      how often update the environment
     */
    def run(environmentSize: (Int, Int), iterations: Int, updateStep: Int): Unit = {
      var environmentManager = EnvironmentManager(environmentSize._1, environmentSize._2)
      val statisticalData = StatisticalData(Time.time)
      createAndShowGUI(statisticalData, Time.time)


      //plot(environmentManager.environment)


      environmentManager = GeneratorClimateChange.generateClimate(environmentSize._1, environmentSize._2, iterations)
        .foldLeft(environmentManager)(addSource)

      environmentManager = GeneratorClimateChange.generateSeason().foldLeft(environmentManager)(addSource)


   */

  //TODO: Da sostituire con Ecosystem
  @tailrec
  def loop(iterations: Int)(updateModel: () => Unit)(updateView: () => Unit): Unit = {


    iterations match {
      case 0 =>
      case _ =>
        updateModel()
        updateView()
        Thread.sleep(60)
        loop(iterations - 1)(updateModel)(updateView)
    }
  }


  //loop(iterations)(update)
  //}

  /* /**
  * Plot the environment calling the view
  *
  * @param environment to plot
  */
 private def plot(environment: Environment): Unit = Property.values.foreach(property => BreezeView.plot(
   environment.map.dropColumns(0.5).dropRows(0.5).mapValues(c => toPercentage(property, c get property) toDouble),
   0,
   100,
   s"${property.toString} (${Property.range(property).minValue}, ${Property.range(property).maxValue})"
 ))*/

  //}
}
