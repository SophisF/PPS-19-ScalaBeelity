package scala

import scala.controller.Controller
import scala.view.View

/**
 * Entry point for the (test) application
 *
 * @author Paolo Baldini
 */
object Main extends App {
  for {
    settings <- View.settingsView
  } yield new Controller(
    settings.coloniesNumber,
    settings.timeGranularity,
    settings.simulationDuration,
    settings.environmentSize
  ).start()
}