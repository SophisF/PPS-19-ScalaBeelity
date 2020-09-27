package scala.view

import scala.controller.Controller

/**
 * Implementation of View.
 */
object View {

  /**
   * View for setup the simulation.
   *
   * @return setting view
   */
  def settingsView: Option[SimulationSettings] = SettingsView.createAndShow

  /**
   * View for show simultaion.
   *
   * @param controller of system
   *
   * @return simulation view
   */
  def simulationView(controller: Controller): SimulationView = new SimulationView(controller)
}