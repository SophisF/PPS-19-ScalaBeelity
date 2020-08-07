package scala.model.property

import scala.model.property.Property._
import breeze.linalg.DenseMatrix

/**
 * Object for a basic filter that contains a matrix representing the gaussian 3d function.
 * It also store the center position (source) on which apply the filter.
 *
 * @author Paolo Baldini
 */
object Filter {
  type Matrix[T] = DenseMatrix[T]
  type PointXY = (Int, Int)
  type SizeWH = (Int, Int)

  case class Filter[T](data: Matrix[Variation[T]], center: PointXY, size: SizeWH)
  case class Variation[T](value: T, property: Property)

  def topBorder[T](filter: Filter[T]): Int = filter.center._2 - (filter.size._2 - 1) / 2
  def rightBorder[T](filter: Filter[T]): Int = filter.center._1 + (filter.size._1 - 1) / 2
  def bottomBorder[T](filter: Filter[T]): Int = filter.center._2 + (filter.size._2 - 1) / 2
  def leftBorder[T](filter: Filter[T]): Int = filter.center._1 - (filter.size._1 - 1) / 2
}