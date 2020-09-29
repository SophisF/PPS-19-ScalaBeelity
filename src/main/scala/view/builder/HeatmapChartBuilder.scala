package scala.view.builder

import java.awt.{BorderLayout, Color, Component, Paint}

import breeze.linalg.DenseMatrix
import breeze.linalg.DenseMatrix.create
import breeze.plot.{Plot, image}
import javax.swing.JPanel
import javax.swing.border.EmptyBorder
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.renderer.PaintScale
import org.jfree.chart.renderer.xy.XYBlockRenderer
import org.jfree.chart.title.PaintScaleLegend
import org.jfree.chart.ui.RectangleEdge.RIGHT
import org.jfree.chart.ui.RectangleInsets
import org.jfree.chart.{ChartPanel, JFreeChart}

import scala.Array.empty

/**
 * Heatmap chart.
 */
private[view] object HeatmapChartBuilder extends ChartBuilder[Array[Array[Double]]] {
  override type ChartType = Component

  override def createChart(data: Array[Array[Double]]): Component =
    createHeatMap(create(data.headOption.getOrElse(empty).length, data length, data reduce((f, s) => f appendedAll s)))

  override def updateChart(chart: ChartType, data: Array[Array[Double]]): Component = createChart(data)

  private def createHeatMap(data: DenseMatrix[Double]): JPanel = {
    val panel = new JPanel(new BorderLayout())
    val plot = (new Plot() += image(data)) plot

    val render = new XYBlockRenderer
    val paintScale: PaintScale = new PaintScale {
      override val getLowerBound = .0
      override val getUpperBound = 100.0

      override def getPaint(value: Double): Paint = value match {
        case v if v < getLowerBound => getLowerBound
        case v if v > getUpperBound => getUpperBound
        case _ => (value - getLowerBound) / (getUpperBound - getLowerBound)
      }
    }
    render setPaintScale paintScale
    plot setRenderer render

    val chart = new JFreeChart(plot)
    val legend = new PaintScaleLegend(paintScale, new NumberAxis)
    legend setPadding new RectangleInsets(25, 10, 25, 10)
    legend setBackgroundPaint panel.getBackground
    legend setPosition RIGHT
    chart addSubtitle legend
    chart.removeLegend()

    panel add new ChartPanel(chart)
    panel setBorder new EmptyBorder(50, 50, 50, 50)
    panel
  }

  private implicit def toColor(value: Double): Color = Color.getHSBColor(.7 - value * .7 toFloat, 1, 1)
}