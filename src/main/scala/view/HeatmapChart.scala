package view

import smile.interpolation.BicubicInterpolation
import smile.plot.swing.{Contour, Palette, PlotPanel, heatmap}

import scala.util.Random


object HeatmapChart {
  //TODO: Il problema è l'interpolazione. Rapporto migliore è 10 volte la grandezza della matrice, ma da verificare meglio

  var Z: Array[Array[Double]] = Array.ofDim[Double](200, 200)

  def createDataset(z: Array[Array[Double]], x: Array[Double], y: Array[Double]): Unit = {
    Z = z
    /*val bicubic = new BicubicInterpolation(x, y, z)

    for (i <- 0 until 200) {
      for (j <- 0 until 200) {
        Z(i)(j) = bicubic.interpolate(i * 0.03, j * 0.03)
        print(Z(i)(j)+" ")
      }
      println("")
    }*/
  }

  def createChart(): PlotPanel = {
    val canvas = heatmap(Z, Palette.jet(256))
    //TODO: Contorni rimovibili in base alle nostre scelte

    canvas.add(Contour.of(Z))
    canvas.panel()
  }

}
