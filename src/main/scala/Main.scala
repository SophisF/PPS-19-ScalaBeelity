package scala

import scala.controller.Controller
import scala.view.View

/**
 * Entry point for the (test) application
 *
 * @author Paolo Baldini
 */
object Main extends App {
  View.createInitialGUI((colonies, updateTime, iterations, dimension) =>
    new Controller(colonies, updateTime, iterations, dimension).start())
}