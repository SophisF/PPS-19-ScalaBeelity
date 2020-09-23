package scala.view

trait SimulationSettings {

  def coloniesNumber: Int
  def timeGranularity: Int
  def simulationDuration: Int
  def environmentSize: Int
}
