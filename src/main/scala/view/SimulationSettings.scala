package scala.view

/**
 * Case class for parameters of the simulation.
 *
 * @param coloniesNumber, number of colonies
 * @param timeGranularity, granularity of time.
 * @param simulationDuration, number of iterations to execute before stop the simulation.
 * @param environmentSize, dimension of the environment.
 */
case class SimulationSettings(
  coloniesNumber: Int,
  timeGranularity: Int,
  simulationDuration: Int,
  environmentSize: (Int, Int)
)
