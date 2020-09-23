package scala.view.builder

import java.awt.{BasicStroke, Color, Component}

import org.jfree.chart.ChartFactory.createXYLineChart
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.chart.{ChartPanel, JFreeChart}
import org.jfree.data.xy.DefaultXYDataset

object SeasonalChartBuilder extends ChartBuilder[Seq[(String, Array[Array[Double]])]] {
  override type ChartType = Component

  override def createChart(data: Seq[(String, Array[Array[Double]])]): Component = {
    val xAxisLabel = "Month"
    val yAxisLabel = "Value in Percentage"
    val dataset = createDataset(data)
    val chart = createXYLineChart("", xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, true)
    customizeChart(chart, data.size)
    new ChartPanel(chart)
  }

  override def updateChart(chart: Component, data: Seq[(String, Array[Array[Double]])]): Component = createChart(data)

  //TODO: vedere se fare impliciti
  private def createDataset(data: Seq[(String, Array[Array[Double]])]) = { // this method creates the data as time series
    val ds = new DefaultXYDataset
    data.foreach(d => ds.addSeries(d._1, d._2.map(_ toArray)))
    ds
  }

  private def customizeChart(chart: JFreeChart, numLines: Int): Unit = { // here we make some customization
    val plot = chart.getXYPlot
    val renderer = new XYLineAndShapeRenderer
    (0 until numLines).foreach(x => {
      // sets paint color for each series
      renderer.setSeriesPaint(x, Color.getHSBColor(x / numLines.toFloat, 1, 1))
      // sets thickness for series (using strokes)
      renderer.setSeriesStroke(x, new BasicStroke(4.0f))
    })
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
    plot.getRangeAxis.setRange(-5, 105)
  }
}
