package scala

import scala.model.Ecosystem

/**
 * Entry point for the (test) application
 *
 * @author Paolo Baldini
 */
object Main extends App {
  /*
  private val EnvironmentSize = (1000, 1000)
  private val Iterations = 1000
  private val UpdateWindow = 10

  println("Testing execution speed with:\n\t" +
    EnvironmentSize._1 * EnvironmentSize._2 + " cells\n\t" +
    Iterations + " iterations")

  val initialTime = System.currentTimeMillis()
  Looper.run(EnvironmentSize, Iterations, UpdateWindow)
  var timeElapsed: Long = System.currentTimeMillis() - initialTime
  val peak = -50

  println(s"Simulation ran in ${timeElapsed} ms\n" +
    s"Average time for an iteration was ${timeElapsed * UpdateWindow / Iterations} ms")

  /*
    Notes: the test is about apply a filter 1 over $UpdateWindow iterations. That led to a total of
    ${Iterations / UpdateWindow} filter applied. Also note that the filter is applied only to a layer (temperature)
    so if you want to update also the other you need 3 times the time. Note also that it's COULD be possible to
    calculate the filters in a parallel way (i'm not sure if it increments something in that i saw that the library yet
    performs some tasks in parallel). But that require to study how to structure the 'cell' entity (if mutable,
    avoiding race hazard; if immutable, collecting the three results).
   */

   */

  Ecosystem.initialize(1)
  Ecosystem.colonies.foreach(c=> println(c.bees.size))


}