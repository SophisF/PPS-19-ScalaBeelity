package scala.controller

import scala.annotation.tailrec

/**
 * Looper of the test. Recursively manage the updates
 */
object Looper {

  /**
   * Exec all the update steps set for the simulation
   *
   * @param iterations number of time to call update
   * @param updateModel function to update model
   * @param updateView function to update the view
   */
  //TODO: Da sostituire con Ecosystem
  @tailrec
  def loop(iterations: Int)(updateModel: () => Unit)(updateView: () => Unit): Unit = iterations match {
    case 0 =>
    case _ =>
      updateModel()
      updateView()
      loop(iterations - 1)(updateModel)(updateView)
  }

  /*
  //TODO: guardate se vi piace... è più generica
  @tailrec
  def loop(iterations: Int)(updatable: () => Unit*): Unit = iterations match {
    case 0 =>
    case _ =>
      updatable foreach (p => p.apply())
      loop(iterations - 1)(updatable :_*)
  }
   */
}
