package scala.view

import java.awt.Color

import breeze.linalg._
import breeze.plot._

import scala.model.environment.property.PropertyType

/**
 * Basic view that allows to plot grids.
 *
 * @author Paolo Baldini
 */
object BreezeView {

  private val Figure_ = Figure()
  private val ColorGradient = (0 until(360, 36)).map(it => new Color(Color.HSBtoRGB(it / 360f, 1, 1)))
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
  def plot(map: DenseMatrix[Double], minValue: Int = 0, maxValue: Int = 100, name: String = i toString): Unit = {
    (Figure_.subplot(i / Figure_.cols + 1, PropertyType.values.size, i) += image(map, GradientPaintScale(minValue, maxValue,
      ColorGradient.toArray))).title = name
    i += 1
  }
}
