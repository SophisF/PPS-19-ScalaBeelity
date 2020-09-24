package scala.view

case class SimulationSettings(
  coloniesNumber: Int,
  timeGranularity: Int,
  simulationDuration: Int,
  environmentSize: (Int, Int)
)
