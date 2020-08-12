package scala.model.property

import breeze.linalg._

import scala.model.matrix._
import scala.model.property.PropertyVariation._
import scala.model.time.EvolvableData

trait ZonePropertySource extends PropertySource with Point with Size

/**
 * Object for a basic filter that contains a matrix representing the gaussian 3d function.
 * It also store the center position (source) on which apply the filter.
 *
 * @author Paolo Baldini
 */
object ZonePropertySource {

  type VariationMatrix = DenseMatrix[Variation]

  case class InstantaneousZonePropertySource(
    data: VariationMatrix,
    x: Int, y: Int,
    width: Int, height: Int
  ) extends ZonePropertySource

  case class ContinuousZonePropertySource(
    var evaluatedPercent: Int,
    inference: (EvolvableData[VariationMatrix], Int) => VariationMatrix,
    x: Int, y: Int,
    width: Int, height: Int,
    fireTime: Int, duration: Int
  ) extends ZonePropertySource with EvolvableData[VariationMatrix]

  //def apply(x: Int, y: Int, width: Int, height: Int, filter: VariationMatrix): InstantaneousPropertySource =
  //  InstantaneousPropertySource(filter, x, y, width, height)

  def apply(x: Int, y: Int, width: Int, height: Int, fireTime: Int, duration: Int, filter: VariationMatrix)
  : ContinuousZonePropertySource = ContinuousZonePropertySource(0, (data, time) => {
    val force = (time - data.fireTime) * 100 / data.duration.toDouble - data.evaluatedPercent
    data.evaluatedPercent += force.toInt
    DenseMatrix.create(filter.rows, filter.cols, filter.data
      .map(variation => Variation(variation.property, (variation.value * force).toInt)))
  }, x, y, width, height, fireTime, duration)

  /*def linearize(filter: ContinuousPropertySource[T], instant: Int): Option[Array[PointVariation[T]]] =
    lastFired(filter, instant) match {
      case None => Option.empty
      case Some(value) => Option.apply(
        linearize(InstantaneousPropertySource(value, filter.x, filter.y, filter.width, filter.height))
      )
    }*/

  def linearize(filter: InstantaneousZonePropertySource): Array[PointVariation] = filter.data.mapPairs((p, v) =>
    PointVariation(v.property, v.value, p._1, p._2)).data

  def sort(variations: PointVariation*): Seq[PointVariation] = variations.sortWith(Point.compare)

  def border(filter: ZonePropertySource)(border: Size.Border): Int = border match {
    case Size.Top | Size.Bottom => filter.y + filter ~ border
    case _ => filter.x + filter ~ border
  }

  def in(x: Int, y: Int, filter: ZonePropertySource): Boolean = {
    x >= border(filter)(Size.Left) && x <= border(filter)(Size.Right) &&
    y >= border(filter)(Size.Top) && y <= border(filter)(Size.Bottom)
  }
}