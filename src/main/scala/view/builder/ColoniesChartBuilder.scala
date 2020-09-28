package scala.view.builder

import java.awt.Color.getHSBColor
import java.awt.geom.Rectangle2D
import java.awt.{BasicStroke, Color, Dimension, GridLayout}

import breeze.plot.Plot
import javax.swing.{JPanel, JTextArea}
import org.jfree.chart.annotations.{XYAnnotation, XYShapeAnnotation}
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.ui.RectangleEdge
import org.jfree.chart.{ChartMouseEvent, ChartMouseListener, ChartPanel, JFreeChart}

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.phenotype.Characteristic.Characteristic
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.utility.Point
import scala.utility.TypeUtilities._

/**
 * Chart for show movement of colonies and their details.
 */
object ColoniesChartBuilder extends ChartBuilder[((Int, Int), StatisticColonies)] {
  override type ChartType = ColoniesChart
  class ColoniesChart(var selectedColony: Option[StatisticColony] = Option.empty) extends JPanel

  override def createChart(data: ((Int, Int), StatisticColonies)): ColoniesChart = newChart(data._1, data._2, None)

  override def updateChart(chart: ColoniesChart, data: ((Int, Int), StatisticColonies)): ColoniesChart =
    newChart(data._1, data._2, chart.selectedColony)

  private def newChart(environmentSize: Dimension, data: StatisticColonies, selectedColony: Option[StatisticColony])
  : ColoniesChart = {
    val panel = new ColoniesChart(selectedColony)
    panel.setLayout(new GridLayout(1,2))

    val plot = new Plot().plot
    plot.getRangeAxis.setRange(0, environmentSize.height)
    plot.getDomainAxis.setRange(0, environmentSize.width)

    data.map(it => colonyToComponent(it._1.center, (it._1.dimension, it._1.dimension), it._1.color))
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

  private def colonyToString(colonyChar: (Colony, Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)])): String = {
    var str = s"Colony info: \n\nPosition: ${colonyChar._1.center x}; ${colonyChar._1.center y}\n\nBees' number: ${colonyChar._1.bees size}"
    colonyChar._2.foreach(t => str = str.+(s"\n\n${t._1}: ${t._2}"))
    str
  }

  private def colonyOverPoint(data: StatisticColonies, x: Int, y: Int): Option[(Colony, Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)])] =
    data.find(d => x >= d._1.center.x - d._1.dimension / 2 && x <= d._1.center.x + d._1.dimension/ 2
      && y >= d._1.center.y - d._1.dimension / 2 && y <= d._1.center.y + d._1.dimension / 2)

  private def mousePosition(_x: Int, _y: Int, plot: XYPlot, chart: ChartPanel): (Int, Int) = {
    val dataArea = chart.getScreenDataArea

    val y = plot.getRangeAxis.java2DToValue(_y, dataArea, RectangleEdge.LEFT)
    val x = plot.getDomainAxis.java2DToValue(_x, dataArea, RectangleEdge.BOTTOM)

    (x toInt, y toInt)
  }

  private implicit def dimensionFrom(tuple: (Int, Int)): Dimension = new Dimension(tuple._1, tuple._2)

  private implicit def colorFrom(value: Double): Color = getHSBColor(value toFloat, 1, 1)
}
