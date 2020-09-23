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

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.phenotype.Characteristic.Characteristic
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.utility.TypeUtilities.StatisticColony


object ColoniesChartBuilder extends ChartBuilder[StatisticColony] {

  override type ChartType = ColoniesChart

  class ColoniesChart(var selectedColony: Option[(Colony, Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)])] = Option.empty) extends JPanel

  override def createChart(data: StatisticColony): ColoniesChart = newChart(data, Option.empty)

  override def updateChart(chart: ColoniesChart, data: StatisticColony): ColoniesChart =
    newChart(data, chart.selectedColony)

  private def newChart(data: StatisticColony, selectedColony: Option[(Colony, Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)])]): ColoniesChart = {
    val panel = new ColoniesChart(selectedColony)
    panel.setLayout(new GridLayout(1,2))

    val plot = new Plot().plot
    plot.getRangeAxis.setRange(0, 100)  // TODO env size
    plot.getDomainAxis.setRange(0, 100)

    data.map(it => colonyToComponent(it._1.center, new Dimension(it._1.dimension, it._1.dimension), getHSBColor(it._1.color toFloat, 1, 1)))
      .foreach(plot.addAnnotation)

    val chart = new ChartPanel(new JFreeChart(plot))
    val infoArea = new JTextArea(selectedColony.map(colonyToString) getOrElse "No colony selected") //TODO provo eliminare Else

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

  private def colonyToString(colonyChar: (Colony, Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)])): String = {
    var str : String = s"Colony info: \n Position: ${colonyChar._1.center x} ${colonyChar._1.center y}\nBees' number: ${colonyChar._1.bees size}"
    colonyChar._2.foreach(t => str = str.+(s"\n ${t._1}, ${t._2}".toString))
    str
  }

  private def colonyOverPoint(data: StatisticColony, x: Int, y: Int): Option[(Colony, Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)])] = {
    data.find(d => x >= d._1.center.x - d._1.dimension / 2 && x <= d._1.center.x + d._1.dimension/ 2 && y >= d._1.center.y - d._1.dimension / 2 && y <= d._1.center.y + d._1.dimension / 2)
  }

  private def mousePosition(_x: Int, _y: Int, plot: XYPlot, chart: ChartPanel): (Int, Int) = {
    val dataArea = chart.getScreenDataArea

    val y = plot.getRangeAxis.java2DToValue(_y, dataArea, RectangleEdge.LEFT)
    val x = plot.getDomainAxis.java2DToValue(_x, dataArea, RectangleEdge.BOTTOM)

    (x toInt, y toInt)
  }
}
