package view

import model.StatisticalData.StatisticalData

trait View {

  def createAndShowGUI(statisticalData: StatisticalData, time: Int)

  def updateGui(statisticalData: StatisticalData, time: Int)

}
