package scala.model.property

import breeze.linalg._

import scala.model.matrix._
import scala.model.property.PropertyVariation._
import scala.model.time.EvolvableData

trait PropertySource extends Point with Size

/**
 * Object for a basic filter that contains a matrix representing the gaussian 3d function.
 * It also store the center position (source) on which apply the filter.
 *
 * @author Paolo Baldini
 */
object PropertySource {
  
  type VariationMatrix = DenseMatrix[Variation]

  case class InstantaneousPropertySource(
    data: VariationMatrix,
    x: Int, y: Int,
    width: Int, height: Int
  ) extends PropertySource

  case class ContinuousPropertySource(
    evaluatedPercent: Int = 0,
    inference: (EvolvableData[VariationMatrix], Int) => VariationMatrix,
    x: Int, y: Int,
    width: Int, height: Int,
    fireTime: Int, duration: Int
  ) extends PropertySource with EvolvableData[VariationMatrix]

  def apply[T](x: Int, y: Int, width: Int, height: Int, filter: VariationMatrix)
  : InstantaneousPropertySource = InstantaneousPropertySource(filter, x, y, width, height)

  def apply[T](x: Int, y: Int, width: Int, height: Int, fireTime: Int, duration: Int, filter: VariationMatrix,
  get: Int): ContinuousPropertySource = ContinuousPropertySource(0, (data, time) => {
    val force = (time - data.fireTime / data.duration) - data.evaluatedPercent
    data.evaluatedPercent += force
    DenseMatrix.create(filter.rows, filter.cols, filter.data
      .map(variation => Variation(variation.property, variation.value * force)))
  }, x, y, width, height, fireTime, duration)

  /*def linearize[T](filter: ContinuousPropertySource[T], instant: Int): Option[Array[PointVariation[T]]] =
    lastFired(filter, instant) match {
      case None => Option.empty
      case Some(value) => Option.apply(
        linearize(InstantaneousPropertySource(value, filter.x, filter.y, filter.width, filter.height))
      )
    }*/

  def linearize[T](filter: InstantaneousPropertySource): Array[PointVariation] = filter.data.mapPairs((p, v) =>
    PointVariation(v.property, v.value, p._1, p._2)).data

  def sort[T](variations: PointVariation*): Seq[PointVariation] = variations.sortWith(Point.compare)

  def border(filter: PropertySource)(border: Size.Border): Int = border match {
    case Size.Top | Size.Bottom => filter.y + filter ~ border
    case _ => filter.x + filter ~ border
  }

  def in(x: Int, y: Int, filter: PropertySource): Boolean = {
    x >= border(filter)(Size.Left) && x <= border(filter)(Size.Right) &&
    y >= border(filter)(Size.Top) && y <= border(filter)(Size.Bottom)
  }
}