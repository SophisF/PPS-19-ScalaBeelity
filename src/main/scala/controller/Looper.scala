package scala.controller

import scala.annotation.tailrec

/** Looper of the simulation. Recursively manage the updates */
object Looper {

  /**
   * Exec all the update steps set for the simulation
   *
   * @param iterations number of time to call the update
   * @param updatable a sequence of update function
   */
  @tailrec
  def loop(iterations: Int)(updatable: () => Unit*): Unit = iterations match {
    case 0 =>
    case _ =>
      updatable foreach (_())
      loop(iterations - 1)(updatable :_*)
  }
}
