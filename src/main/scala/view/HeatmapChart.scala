package scala.view

import smile.plot.swing.{Palette, PlotPanel, heatmap}

class HeatmapChart[T <: Array[Array[Double]]] extends Chart[T] {

  override def createChart(data: T): PlotPanel = {
    val Z: Array[Array[Double]] = data.map(_.toArray)
    val X = data.headOption.getOrElse(Array.empty).indices.toArray.map(_.toDouble)
    val Y = (0 until data.length).toArray.map(_.toDouble)

    val canvas = heatmap(X, Y, Z, Palette.redgreen(128))
    canvas.panel()
  }
}
