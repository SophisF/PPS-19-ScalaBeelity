package scala.view

import scala.controller.Controller

object View {

  def settingsView: Option[SimulationSettings] = SettingsView.createAndShow

  def simulationView(controller: Controller): SimulationView = new SimulationView(controller)
}