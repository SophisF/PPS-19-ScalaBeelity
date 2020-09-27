package scala.view.builder

import java.awt.Component

/**
 * Trait for all chart.
 */
trait ChartBuilder[T] {
  type ChartType <: Component

  /**
   * Create chart for the first time.
   *
   * @param data to show in chart
   *
   * @return chart
   */
  def createChart(data: T): ChartType

  /**
   * Update chart.
   *
   * @param chart to update with new data.
   * @param data to show in chart
   *
   * @return chart
   */
  def updateChart(chart: ChartType, data: T): ChartType
}