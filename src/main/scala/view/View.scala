package scala.view

import scala.controller.Controller

trait View {

  def createSimulationGUI()

  def update()

}
object View {

  val initialView = new StartViewImpl

  def createInitialGUI(callback: (Int, Int, Int, Int) => Unit): Unit = initialView.createAndShowGUI(callback)

  class ViewImpl(controller: Controller) extends View {

    val simulationView = new ChartViewImpl(controller)

    override def createSimulationGUI(): Unit = simulationView.createAndShowGUI()

    override def update(): Unit = simulationView.updateGui()
  }
}