package view

import smile.plot.swing.{Palette, PlotPanel, heatmap}


class HeatmapChart[T <: Array[Array[Double]]] extends Chart[T] {

  override def createChart(data: T): PlotPanel = {
    val Z: Array[Array[Double]] = data.map(_.toArray).toArray
    val X = (0 until 200).toArray.map(_.toDouble)
    val Y = (0 until 200).toArray.map(_.toDouble)

    val canvas = heatmap(X, Y, Z, Palette.redgreen(128))
    //TODO: Contorni rimovibili in base alle nostre scelte
    //canvas.add(Contour.of(Z))
    canvas.panel()
  }

}
