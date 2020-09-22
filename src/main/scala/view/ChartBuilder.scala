package scala.view

import java.awt.Component

trait ChartBuilder[T] {
  type ChartType <: Component

  def createChart(data: T): ChartType

  def updateChart(chart: ChartType, data: T): ChartType
}