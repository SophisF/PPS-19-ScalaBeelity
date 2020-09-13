package scala.view

import java.awt.Color

import breeze.linalg._
import breeze.plot._

import scala.model.environment.property.{Property, PropertyType}

/**
 * Basic view that allows to plot grids.
 *
 * @author Paolo Baldini
 */
object View {

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
  def plot(map: DenseMatrix[Double], name: String = i toString): Unit = {
    println(map.data.min + " " + map.data.max)
    (Figure_.subplot(i / Figure_.cols + 1, PropertyType.values.size, i) += image(map, GradientPaintScale(0, 100,
      ColorGradient.toArray))).title = name
    i += 1
  }
}
