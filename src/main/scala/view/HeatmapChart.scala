package view

import smile.interpolation.BicubicInterpolation
import smile.plot.swing.{Contour, Palette, PlotPanel, heatmap}


object HeatmapChart {
  //TODO: Take parameters and set x, y, z.

  val Z: Array[Array[Double]] = Array.ofDim[Double](101, 101)

  def createDataset(z: Array[Array[Double]], x: Array[Double], y: Array[Double]): Unit = {
    val bicubic = new BicubicInterpolation(x, y, z)
    for (i <- 0 to 100) {
      for (j <- 0 to 100) {
        Z(i)(j) = bicubic.interpolate(i * 0.03, j * 0.03)
      }
    }
  }

  def createChart(): PlotPanel = {
    val canvas = heatmap(Z, Palette.jet(256))
    canvas.add(Contour.of(Z))
    canvas.panel()
  }
}
