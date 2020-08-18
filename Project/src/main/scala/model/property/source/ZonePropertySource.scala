package scala.model.property.source

import breeze.linalg._

import scala.model.matrix._
import scala.model.property.Property
import scala.model.property.PropertyVariation._
import scala.model.time.{FiniteData, Time}

trait ZonePropertySource[T <: Property] extends PropertySource[T] with Point with Size

/**
 * Object for a basic filter that contains a matrix representing the gaussian 3d function.
 * It also store the center position (source) on which apply the filter.
 *
 * @author Paolo Baldini
 */
object ZonePropertySource {

  case class InstantaneousZonePropertySource[T <: Property](
    data: DenseMatrix[Variation[T]],
    x: Int, y: Int,
    width: Int, height: Int
  ) extends ZonePropertySource[T]

  case class ContinuousZonePropertySource[T <: Property](
    filter: DenseMatrix[Variation[T]],
    x: Int, y: Int,
    width: Int, height: Int,
    fireTime: Int, duration: Int
  ) extends ZonePropertySource[T] with FiniteData[DenseMatrix[Variation[T]]] {
    var evaluated: Int = 0
  }

  //def apply(x: Int, y: Int, width: Int, height: Int, filter: VariationMatrix): InstantaneousPropertySource =
  //  InstantaneousPropertySource(filter, x, y, width, height)

  implicit def nextValue[T <: Property](data: ContinuousZonePropertySource[T]): DenseMatrix[Variation[T]] = {
    val force = (Time.time - data.fireTime) * 100 / data.duration.toDouble - data.evaluated
    data.evaluated += force.toInt
    DenseMatrix.create(data.filter.rows, data.filter.cols, data.filter.data
      .map(variation => Variation(/*(variation.value * force)*/5.asInstanceOf[T#ValueType])))
  }

  def apply[T <: Property](x: Int, y: Int, width: Int, height: Int, fireTime: Int, duration: Int, filter: DenseMatrix[Variation[T]])
  : ContinuousZonePropertySource[T] = ContinuousZonePropertySource(filter, x, y, width, height, fireTime, duration)

  /*def linearize(filter: ContinuousPropertySource[T], instant: Int): Option[Array[PointVariation[T]]] =
    lastFired(filter, instant) match {
      case None => Option.empty
      case Some(value) => Option.apply(
        linearize(InstantaneousPropertySource(value, filter.x, filter.y, filter.width, filter.height))
      )
    }*/

  def linearize[T <: Property](filter: InstantaneousZonePropertySource[T]): Array[PointVariation[T]] =
    filter.data.mapPairs((p, v) => PointVariation(v.value, p._1, p._2)).data

  def sort[T <: Property](variations: PointVariation[T]*): Seq[PointVariation[T]] = variations.sortWith(Point.compare)

  def border[T <: Property](filter: ZonePropertySource[T])(border: Size.Border): Int = border match {
    case Size.Top | Size.Bottom => filter.y + filter ~ border
    case _ => filter.x + filter ~ border
  }

  def in[T <: Property](x: Int, y: Int, filter: ZonePropertySource[T]): Boolean = {
    x >= border(filter)(Size.Left) && x <= border(filter)(Size.Right) &&
    y >= border(filter)(Size.Top) && y <= border(filter)(Size.Bottom)
  }
}