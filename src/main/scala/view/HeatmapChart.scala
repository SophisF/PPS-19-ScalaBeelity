package view

import smile.interpolation.BicubicInterpolation
import smile.plot.swing.{Contour, Palette, heatmap}


object HeatmapChart {
  //TODO: Il problema è l'interpolazione. Rapporto migliore è 10 volte la grandezza della matrice, ma da verificare meglio

  val Z = Array.ofDim[Double](201, 201)


  def createDataset(z: Array[Array[Double]], x: Array[Double], y: Array[Double]) = {
    val bicubic = new BicubicInterpolation(x, y, z)
    for (i <- 0 to 200) {
      for (j <- 0 to 200) {
        Z(i)(j) = bicubic.interpolate(i * 0.03, j * 0.03)
        print(Z(i)(j)+" ")
      }
      println("")
    }
    Z
  }

  def createChart() = {
    val canvas = heatmap(Z, Palette.jet(256))
    //TODO: Contorni rimovibili in base alle nostre scelte

    canvas.add(Contour.of(Z))
    canvas.panel()
  }

}
