package scala.view

import java.awt.Color.getHSBColor

import scala.utility.Point
import java.awt.{BasicStroke, Color, Dimension, GridLayout}

import breeze.plot.Plot
import javax.swing.{JPanel, JTextArea}
import org.jfree.chart.{ChartMouseEvent, ChartMouseListener, ChartPanel, JFreeChart}
import org.jfree.chart.annotations.{XYAnnotation, XYShapeAnnotation}
import org.jfree.chart.ui.RectangleEdge
import java.awt.geom.Rectangle2D

import org.jfree.chart.plot.XYPlot

object ColoniesChartBuilder extends ChartBuilder[Seq[(Point, Int, Double)]] {
  override type ChartType = ColoniesChart

  class ColoniesChart(var selectedColony: Option[(Point, Int, Double)] = Option.empty) extends JPanel

  override def createChart(data: Seq[(Point, Int, Double)]): ColoniesChart = newChart(data, Option.empty)

  override def updateChart(chart: ColoniesChart, data: Seq[(Point, Int, Double)]): ColoniesChart =
    newChart(data, chart.selectedColony)

  private def newChart(data: Seq[(Point, Int, Double)], selectedColony: Option[(Point, Int, Double)]): ColoniesChart = {
    val panel = new ColoniesChart(selectedColony)
    panel.setLayout(new GridLayout(1,2))

    val plot = new Plot().plot
    plot.getRangeAxis.setRange(0, 100)  // TODO env size
    plot.getDomainAxis.setRange(0, 100)

    data.map(it => colonyToComponent(it _1, new Dimension(it _2, it _2), getHSBColor(it._3 toFloat, 1, 1)))
      .foreach(plot.addAnnotation)

    val chart = new ChartPanel(new JFreeChart(plot))
    val infoArea = new JTextArea(selectedColony.map(colonyToString) getOrElse "No colony selected")

    chart.addChartMouseListener(new ChartMouseListener {
      override def chartMouseClicked(event: ChartMouseEvent): Unit = { }

      override def chartMouseMoved(event: ChartMouseEvent): Unit = {
        val position = mousePosition(event.getTrigger.getPoint x, event.getTrigger.getPoint y, plot, chart)
        val selectedColony = colonyOverPoint(data, position _1, position _2)
        if (panel.selectedColony != selectedColony) {
          panel.selectedColony = selectedColony
          panel.repaint()
        }
      }
    })

    panel add chart
    panel add infoArea
    panel
  }

  private def colonyToComponent(point: Point, size: Dimension, color: Color): XYAnnotation = new XYShapeAnnotation(
    new Rectangle2D.Double(point x, point y, size width, size height), new BasicStroke(2f), color, color)

  private def colonyToString(colony: (Point, Int, Double)): String = "Colony info:\n" +
    s"Position: ${colony._1 x} ${colony._1 y}\nSize: ${colony _2} ${colony _2}\nAggressivity: ${colony _3}"

  private def colonyOverPoint(data: Seq[(Point, Int, Double)], x: Int, y: Int): Option[(Point, Int, Double)] =
    data.find(d => x >= d._1.x - d._2 / 2 && x <= d._1.x + d._2 / 2 && y >= d._1.y - d._2 / 2 && y <= d._1.y + d._2 / 2)

  private def mousePosition(_x: Int, _y: Int, plot: XYPlot, chart: ChartPanel): (Int, Int) = {
    val dataArea = chart.getScreenDataArea

    val y = plot.getRangeAxis.java2DToValue(_y, dataArea, RectangleEdge.LEFT)
    val x = plot.getDomainAxis.java2DToValue(_x, dataArea, RectangleEdge.BOTTOM)

    (x toInt, y toInt)
  }
}
