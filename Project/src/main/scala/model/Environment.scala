package scala.model

import breeze.linalg.DenseMatrix

import scala.model.matrix._
import scala.model.property.PropertySource.{InstantaneousPropertySource, border, in, linearize}
import scala.model.property.PropertyVariation.PointVariation

/**
 * A first scratch of the environment class.
 *
 * @param map of the environment. Represented as a grid.
 *
 * @author Paolo Baldini
 */
case class Environment private (map: DenseMatrix[Cell]) {

  /**
   * Apply a filter to the environment
   *
   * @param filter to apply
   * @return modified environment
   */
  def +(filter: InstantaneousPropertySource): Environment = new Environment(map.mapPairs((pointXY, cell) =>
    pointXY match {
      case p if in(p._1, p._2, filter) => cell + filter.data(p._1 - border(filter)(Size.Left), p._2 - border(filter)(Size.Top))
      case _ => cell
    }))

  def +(filters: InstantaneousPropertySource*): Environment = modify(filters.map(linearize).map(_.filter(_.value > 0))
    .reduce((_1, _2) => _1.appendedAll(_2)).sortWith(Point.compare):_*)

  def modify(variations: PointVariation*): Environment = new Environment(
    map.mapPairs((pointXY, cell) => variations.headOption match {
      case None => cell
      case Some(variation) if Point.equals(variation, Point.toPoint(pointXY)) => cell + variation
    }))
}
object Environment {
  type Matrix[T] = Array[Array[T]]
  type SizeWH = (Int, Int)

  /**
   * Create a new environment
   *
   * @param size of the grid/map
   * @param defaultCell initial value for the cells of the map
   * @return the environment
   */
  def apply(size: SizeWH, defaultCell: Cell) = new Environment(DenseMatrix.create(size._1, size._2, Iterator
    .continually(defaultCell).take(size._1 * size._2).toArray))

  /**
   * Apply a filter to an environment
   *
   * @param environment to which apply the filter
   * @param filter to apply
   * @return an environment to which is applied the filter
   */
  def applyFilter(environment: Environment, filter: InstantaneousPropertySource): Environment = environment + filter
}