package scala.controller

import scala.annotation.tailrec

/**
 * Simply controller of the test
 *
 * @author Paolo Baldini
 */
object Looper {
  @tailrec
  def loop(iterations: Int)(updateModel: () => Unit)(updateView: () => Unit): Unit = iterations match {
    case 0 =>
    case _ =>
      updateModel()
      updateView()
      loop(iterations - 1)(updateModel)(updateView)
  }
}
