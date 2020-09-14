package view

import java.awt.Component

trait Chart[T] {

  //def createDataset()

  def createChart(data: T) : Component
}
