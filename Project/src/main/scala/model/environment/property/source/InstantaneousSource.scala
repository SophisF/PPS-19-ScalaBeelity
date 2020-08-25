package scala.model.environment.property.source

import breeze.linalg._

import scala.model.environment.matrix.Size
import scala.model.environment.matrix.Point.toPoint
import scala.model.environment.matrix.Zone.border
import scala.model.environment.property.Property
import scala.model.environment.property.Variation._

sealed trait InstantaneousSource[T <: Property] extends ZoneSource[T]

/**
 * Object for a basic filter that contains a matrix representing the gaussian 3d function.
 * It also store the center position (source) on which apply the filter.
 *
 * @author Paolo Baldini
 */
object InstantaneousSource {

  case class InstantaneousSourceImpl[T <: Property](
    filter: DenseMatrix[GenericVariation[T]],
    x: Int, y: Int,
    width: Int, height: Int
  ) extends InstantaneousSource[T]

  def indexed[T <: Property](source: InstantaneousSource[T], width: Int, height: Int): Seq[(Int, GenericVariation[T])] = {
    val leftDelta: Int = border(source)(Size.Left)
    val topDelta: Int = border(source)(Size.Top)

    source.filter.data.zipWithIndex.map(p => (toPoint(p._2, source.width), p._1))
      .map(p => ((height - p._1.y - topDelta) * width + (p._1.x + leftDelta), p._2))
      .filter(p => p._1 >= 0 && p._1 < width * height).toList
  }

  def linearize[T <: Property](source: InstantaneousSource[T]): Seq[PointVariation[T]] = source.filter.data
    .zipWithIndex.map(p => (p._1, toPoint(p._2, source.width))).map(p => PointVariation(p._1.value, p._2.x, p._2.y))
}