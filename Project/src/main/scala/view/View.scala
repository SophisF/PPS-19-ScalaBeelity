package scala.view

import java.awt.Color

import breeze.linalg._
import breeze.plot._

import scala.model.property.Property

/**
 * Basic view that allows to plot grids.
 *
 * @author Paolo Baldini
 */
object View {

  private val Figure_ = Figure()
  private val ColorGradient = Array(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED)
  private var i = 0 // TODO :/

  /**
   * Clean the view
   */
  def apply(): Unit = { Figure_.clear(); i = 0 }

  /**
   * Plot the specified map/grid.
   *
   * @param map to plot
   */
  def plot(map: DenseMatrix[Double]): Unit = {
    (Figure_.subplot(i / Figure_.cols + 1, Property.values.size, i) += image(map, GradientPaintScale(0, 100,
      ColorGradient))).title = i.toString
    i += 1
  }
}
