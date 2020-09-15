package view

import model.StatisticalData.StatisticalData

trait View {

  def updateGui(statisticalData: StatisticalData, time: Int)

}
