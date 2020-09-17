package scala.view

import Array.empty
import breeze.linalg.DenseMatrix
import breeze.linalg.DenseMatrix.create
import breeze.plot.{Plot, image}
import org.jfree.chart.{ChartPanel, JFreeChart}
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.renderer.PaintScale
import org.jfree.chart.renderer.xy.XYBlockRenderer
import org.jfree.chart.title.PaintScaleLegend
import org.jfree.chart.ui.RectangleEdge.RIGHT
import org.jfree.chart.ui.RectangleInsets
import java.awt.{BorderLayout, Color, Component, Paint}
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

import scala.view.HeatmapChart.createHeatMap

class HeatmapChart[T <: Array[Array[Double]]] extends Chart[T] {

  override def createChart(data: T): Component = createHeatMap(create(data.headOption.getOrElse(empty).length,
    data length, data reduce((r1, r2) => r1 appendedAll r2)))
}

object HeatmapChart {

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

    panel add new ChartPanel(chart)
    panel setBorder new EmptyBorder(50, 50, 50, 50)
    panel
  }

  private implicit def toColor(value: Double): Color = Color.getHSBColor(.7 - value * .7 toFloat, 1, 1)
}