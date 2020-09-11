package view

import java.awt.{BasicStroke, Color, GridLayout}

import javax.swing.{JLabel, JPanel}
import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.data.xy.DefaultXYDataset

object SeasonalChart {

  def createChart = { // this method will create the chart panel containin the graph
    val xAxisLabel = "Month"
    val yAxisLabel = "Value"
    val dataset = createDataset
    val chart = ChartFactory.createXYLineChart("", xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, true)
    customizeChart(chart)
    new ChartPanel(chart)
  }

  private def createDataset = { // this method creates the data as time seris
    val ds = new DefaultXYDataset
    val data0: Array[Array[Double]] = Array(Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
      Array(20, 21, 22, 23, 24, 25, 26, 25, 24, 23, 22, 21))
    ds.addSeries("Temperature", data0)
    val data1: Array[Array[Double]] = Array(Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
      Array(30, 31, 32, 33, 34, 35, 35, 34, 33, 32, 31, 30))
    ds.addSeries("Humidity", data1)
    val data2: Array[Array[Double]] = Array(Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
      Array(10.30, 10.31, 10.32, 10.33, 10.34, 10.35, 10.35, 10.34, 10.33, 10.32, 10.31, 10.30))
    ds.addSeries("Pressure", data2)

    ds
  }

  private def customizeChart(chart: JFreeChart): Unit = { // here we make some customization
    val plot = chart.getXYPlot
    val renderer = new XYLineAndShapeRenderer
    // sets paint color for each series
    renderer.setSeriesPaint(0, Color.RED)
    renderer.setSeriesPaint(1, Color.GREEN)
    renderer.setSeriesPaint(2, Color.YELLOW)
    // sets thickness for series (using strokes)
    renderer.setSeriesStroke(0, new BasicStroke(4.0f))
    renderer.setSeriesStroke(1, new BasicStroke(3.0f))
    renderer.setSeriesStroke(2, new BasicStroke(2.0f))
    // sets paint color for plot outlines
    plot.setOutlinePaint(Color.BLUE)
    plot.setOutlineStroke(new BasicStroke(2.0f))
    // sets renderer for lines
    plot.setRenderer(renderer)
    // sets plot background
    plot.setBackgroundPaint(Color.DARK_GRAY)
    // sets paint color for the grid lines
    plot.setRangeGridlinesVisible(true)
    plot.setRangeGridlinePaint(Color.BLACK)
    plot.setDomainGridlinesVisible(true)
    plot.setDomainGridlinePaint(Color.BLACK)
  }
}
